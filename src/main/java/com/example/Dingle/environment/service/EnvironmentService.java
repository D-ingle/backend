package com.example.Dingle.environment.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.environment.entity.CrimeProneArea;
import com.example.Dingle.environment.repository.CrimeProneAreaOpenApiRepository;
import com.example.Dingle.environment.repository.CrimeProneAreaRepository;
import com.example.Dingle.environment.util.CrimeTypeMapper;
import com.example.Dingle.environment.util.GeometryConverter;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class EnvironmentService {

    private final CrimeProneAreaOpenApiRepository crimeProneAreaOpenApiRepository;
    private final CrimeProneAreaRepository crimeProneAreaRepository;
    private final DistrictRepository districtRepository;
    private final ObjectMapper objectMapper;

    private static final String TARGET_SGG_CODE = "11530";
    private static final int PAGE_SIZE = 2;

    @Transactional
    public void saveCrimeProneArea() throws Exception {

        District district = districtRepository.findByDistrictName("구로구")
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        int pageNo = 1;

        while (true) {

            // 1. API 호출
            String raw = crimeProneAreaOpenApiRepository.fetch(pageNo, PAGE_SIZE);
            System.out.println(raw);

            // 2. JSON 파싱
            JsonNode root = objectMapper.readTree(raw);
            JsonNode bodyNode = root.get("body");

            if (bodyNode == null || bodyNode.isNull()) {
                log.info("[CrimeAPI] body is null. stop. pageNo={}", pageNo);
                break;
            }

            if (!bodyNode.isArray()) {
                log.warn("[CrimeAPI] body exists but not array. pageNo={} body={}",
                        pageNo, bodyNode);
                break;
            }

            if (bodyNode.size() == 0) {
                log.info("[CrimeAPI] body empty array. stop. pageNo={}", pageNo);
                break;
            }

            List<CrimeProneArea> saveTargets = new ArrayList<>();

            // 3. 구로구 필터
            for (JsonNode item : bodyNode) {

                String sggCode = item.path("STDG_SGG_CD").asText();
                if (!sggCode.startsWith(TARGET_SGG_CODE)) continue;

                CrimeProneArea area = new CrimeProneArea(
                        district,
                        CrimeTypeMapper.from(item.path("TYPE_NM").asText()),
                        safeParseInt(item.path("GRD").asText()),
                        GeometryConverter.toMultiPolygon(item.path("GEOM").asText())
                );

                saveTargets.add(area);
            }

            // 4. DB 저장
            if (!saveTargets.isEmpty()) {
                crimeProneAreaRepository.saveAll(saveTargets);
                log.info("[CrimeAPI] pageNo={} saved {} (구로구)", pageNo, saveTargets.size());
            } else {
                log.info("[CrimeAPI] pageNo={} 구로구 데이터 없음", pageNo);
            }

            // 5. 마지막 페이지 판단
            if (bodyNode.size() < PAGE_SIZE) {
                log.info("[CrimeAPI] last page reached. pageNo={}", pageNo);
                break;
            }

            pageNo++;
            Thread.sleep(200);
        }
    }

    private int safeParseInt(String v) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            return 0;
        }
    }
}
