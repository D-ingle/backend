package com.example.Dingle.safety.service;

import com.example.Dingle.safety.type.CrimeType;
import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.safety.repository.CrimeProneAreaOpenApiRepository;
import com.example.Dingle.safety.repository.CrimeProneAreaRepository;
import com.example.Dingle.safety.util.CrimeTypeMapper;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrimeService {

    private final CrimeProneAreaOpenApiRepository crimeProneAreaOpenApiRepository;
    private final CrimeProneAreaRepository crimeProneAreaRepository;
    private final DistrictRepository districtRepository;
    private final ObjectMapper objectMapper;

    private static final String TARGET_SGG_CODE = "11530";
    private static final int PAGE_SIZE = 100;

    @Transactional
    public void saveCrimeProneArea() throws Exception {

        District district = districtRepository.findByDistrictName("구로구")
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        int pageNo = 1;

        while (true) {
            // 1. API 호출
            String raw = crimeProneAreaOpenApiRepository.fetch(pageNo, PAGE_SIZE);

            // 2. JSON 파싱
            JsonNode root = objectMapper.readTree(raw);
            JsonNode bodyNode = root.get("body");

            // 3. 구로구 필터
            for (JsonNode item : bodyNode) {
                String sggCode = item.path("STDG_SGG_CD").asText();
                if (!sggCode.startsWith(TARGET_SGG_CODE)) continue;

                CrimeType crimeType = CrimeTypeMapper.from(item.path("TYPE_NM").asText());
                int riskLevel = item.path("GRD").asInt();

                String wkt = item.path("GEOM").asText();

                crimeProneAreaRepository.insertNative(
                        district.getId(),
                        crimeType.name(),
                        riskLevel,
                        wkt
                );
            }

            if (bodyNode.size() < PAGE_SIZE) break;
            pageNo++;
            Thread.sleep(200);
        }
    }
}
