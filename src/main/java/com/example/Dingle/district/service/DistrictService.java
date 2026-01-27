package com.example.Dingle.district.service;

import com.example.Dingle.district.dto.request.DistrictRegisterDto;
import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public void register(DistrictRegisterDto request) {
        if (districtRepository.existsByDistrictName(request.getDistrictName())) {
            throw new BusinessException(BusinessErrorMessage.DUPLICATE_DISTRICT);
        }

        districtRepository.save(
                new District(request.getDistrictName())
        );
    }
}
