package com.example.Dingle.noise.service.construction;

import com.example.Dingle.noise.dto.construction.ConstructionLocationDTO;
import com.example.Dingle.noise.dto.construction.ConstructionResponse;
import com.example.Dingle.noise.dto.construction.ConstructionRow;
import com.example.Dingle.noise.repository.ConstructionAPIRepository;
import com.example.Dingle.util.map.CoordinateConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConstructionService {
    private static final int PAGE_SIZE = 1000;
    private static final Pattern GU_PATTERN = Pattern.compile("([가-힣]+구)");

    private final ConstructionAPIRepository constructionAPIRepository;

    public ConstructionService(ConstructionAPIRepository constructionAPIRepository) {
        this.constructionAPIRepository = constructionAPIRepository;
    }

    public List<ConstructionLocationDTO> getConstructionLocations(String districtName) {

        ConstructionResponse firstResponse = constructionAPIRepository.fetchConstructionData(districtName, 1, PAGE_SIZE);

        int totalCount = firstResponse.getBody().getTotalCount();

        List<ConstructionLocationDTO> result = new ArrayList<>();
        result.addAll(mapRowToLocation(extractRows(firstResponse), districtName));

        int pageNo = 2;
        while((pageNo - 1) * PAGE_SIZE < totalCount) {
            ConstructionResponse response = constructionAPIRepository.fetchConstructionData(districtName, pageNo, PAGE_SIZE);
            result.addAll(mapRowToLocation(extractRows(response), districtName));
            pageNo++;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }

    private List<ConstructionRow> extractRows(ConstructionResponse response) {
        if (response == null) return List.of();
        if (response.getBody() == null) return List.of();
        if (response.getBody().getItems() == null) return List.of();
        if (response.getBody().getItems().getItem() == null) return List.of();
        return response.getBody().getItems().getItem();
    }

    private List<ConstructionLocationDTO> mapRowToLocation(List<ConstructionRow> rows, String districtName) {
        if(rows == null || rows.isEmpty()) return List.of();

        LocalDate today = LocalDate.now();

        return rows.stream()
                .filter(row -> hasName(row.getName()))
                .filter(row -> districtName.equals(extractDistrict(pickAddress(row))))
                .filter(row -> isNotFinished(row.getEndDate(), today))
                .map(this::toLocation)
                .toList();
    }

    private String pickAddress(ConstructionRow row) {
        String a = row.getRoadAddress();
        if(a == null || a.isEmpty()) return "";
        return a;
    }

    private boolean hasName(String name) {
        if (name == null) return false;
        String n = name.trim();
        if (n.isEmpty()) return false;
        return !n.equals("-");
    }

    private boolean isNotFinished(String date, LocalDate today){
        LocalDate endDate = parseDate(date);
        if(endDate == null) return false;

        return !endDate.isBefore(today);
    }

    private ConstructionLocationDTO toLocation(ConstructionRow row) {
        String district = extractDistrict(row.getRoadAddress());

        Double X = row.getX();
        Double Y = row.getY();

        Double longitude = null;
        Double latitude = null;

        if(X != null && Y != null ){
            double[] lonLat = CoordinateConverter.convert3857(X,Y);
            longitude = lonLat[0];
            latitude = lonLat[1];
        }

        return ConstructionLocationDTO.builder()
                .district(district)
                .name(row.getName())
                .startDate(parseDate(row.getStartDate()))
                .endDate(parseDate(row.getEndDate()))
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    private String extractDistrict(String address){
        if(address == null) return address;

        Matcher matcher = GU_PATTERN.matcher(address);
        return matcher.find() ? matcher.group(1) : null;
    }

    private LocalDate parseDate(String date){
        if(date == null) return null;
        date = date.trim();
        if(date.isEmpty() || "-".equals(date)) return null;

        if(date.matches("\\d{8}"))
            return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);

        return LocalDate.parse(date);
    }
}
