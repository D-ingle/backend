package com.example.Dingle.infra.service;

import com.example.Dingle.infra.dto.MarketLocationDTO;
import com.example.Dingle.infra.dto.MarketResponse;
import com.example.Dingle.infra.dto.MarketRow;
import com.example.Dingle.infra.repository.MarketOpenAPIRepository;
import com.example.Dingle.util.map.CoordinateConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MarketService {

    private static final int PAGE_SIZE = 1000;
    private static final Pattern GU_PATTERN = Pattern.compile("([가-힣]+구)");

    private final MarketOpenAPIRepository marketOpenAPIRepository;

    public MarketService(MarketOpenAPIRepository marketOpenAPIRepository) {
        this.marketOpenAPIRepository = marketOpenAPIRepository;
    }

    public List<MarketLocationDTO> getMarketLocations(String districtName){

        MarketResponse firstResponse = marketOpenAPIRepository.fetchMarketData(districtName, 1, PAGE_SIZE);

        int totalCount = firstResponse.getListTotalCount();
        List<MarketLocationDTO> result = new ArrayList<>();
        result.addAll(mapRowToLocations(firstResponse.getRow(), districtName));

        int startIndex = PAGE_SIZE + 1;

        while(startIndex <= totalCount){
            int endsIndex = Math.min(startIndex + PAGE_SIZE - 1, totalCount);

            MarketResponse response = marketOpenAPIRepository.fetchMarketData(districtName, startIndex, endsIndex);

            if (response != null && response.getRow() != null) {
                result.addAll(mapRowToLocations(firstResponse.getRow(), districtName));
            }

            startIndex += PAGE_SIZE;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }

    private List<MarketLocationDTO> mapRowToLocations(List<MarketRow> rows, String districtName){
        if( rows == null || rows.isEmpty() ) return List.of();

        return rows.stream()
                .filter(row -> districtName.equals(extractDistrict(resolveAddress(row))))
                .map(this::toLocation)
                .toList();
    }

    private MarketLocationDTO toLocation(MarketRow row){
        String address = resolveAddress(row);
        String district = extractDistrict(address);

        Double X = row.getX();
        Double Y = row.getY();

        Double longitude = null;
        Double latitude = null;

        if(X != null && Y != null ){
            double[] lonLat = CoordinateConverter.convert(X,Y);
            longitude = lonLat[0];
            latitude = lonLat[1];
        }

        return MarketLocationDTO.builder()
                .district(district)
                .name(row.getName())
                .loadAddress(row.getRoadAddress())
                .longitude(longitude)
                .latitude(latitude)
                .build();

    }

    private String resolveAddress(MarketRow row){
        String address = row.getRoadAddress();
        return (address != null) ? address : row.getSiteAddress();
    }

    private String extractDistrict(String address){
        if(address == null) return address;

        Matcher matcher = GU_PATTERN.matcher(address);
        return matcher.find() ? matcher.group(1) : null;
    }
}
