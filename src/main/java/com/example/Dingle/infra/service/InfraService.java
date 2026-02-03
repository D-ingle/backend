package com.example.Dingle.infra.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.dto.CctvLocationDto;
import com.example.Dingle.infra.dto.MarketLocationDTO;
import com.example.Dingle.infra.dto.convenienceStore.ConvenienceStoreLocationDTO;
import com.example.Dingle.infra.entity.Infra;
import com.example.Dingle.infra.repository.InfraRepository;
import com.example.Dingle.infra.type.Category;
import com.example.Dingle.infra.type.InfraType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfraService {
    private final CctvService cctvService;
    private final MarketService marketService;
    private final DistrictRepository districtRepository;
    private final InfraRepository infraRepository;
    private final ConvenienceStoreService convenienceStoreService;

    @Transactional
    public void saveCctvInfra(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<CctvLocationDto> cctvLocations = cctvService.getCctvLocations(districtName);

        List<Infra> infraList = cctvLocations.stream()
                .map(dto -> new Infra(
                        district,
                        Category.SAFETY,
                        InfraType.CCTV,
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();

        infraRepository.saveAll(infraList);
    }

    @Transactional
    public void saveMarketInfra(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<MarketLocationDTO> marketLocations = marketService.getMarketLocations(districtName);

        List<Infra> market = marketLocations.stream()
                .map(dto -> new Infra(
                        district,
                        Category.CONVENIENCE,
                        InfraType.MARKET,
                        dto.getName(),
                        dto.getLoadAddress(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();
        infraRepository.saveAll(market);
    }

    @Transactional
    public void saveConvenienceStoreInfra(String districtName) {
        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        String districtCode = district.getDistrictCode();

        List<ConvenienceStoreLocationDTO> convenienceStoreLocations = convenienceStoreService.getConvenienceStoreLocations(districtCode);

        List<Infra> convenienceStore = convenienceStoreLocations.stream()
                .map(dto -> new Infra(
                        district,
                        Category.CONVENIENCE,
                        InfraType.CONVENIENCE_STORE,
                        dto.getName(),
                        dto.getLoadAddress(),
                        dto.getLongitude(),
                        dto.getLatitude()
                ))
                .toList();
        infraRepository.saveAll(convenienceStore);
    }
}
