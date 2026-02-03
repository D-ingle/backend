package com.example.Dingle.noise.service.population;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.noise.dto.population.FloatingPopulationResponse;
import com.example.Dingle.noise.dto.population.FloatingPopulationRowDto;
import com.example.Dingle.noise.entity.FloatingPopulation;
import com.example.Dingle.noise.repository.FloatingPopulationRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FloatingPopulationService {

    private final FloatingPopulationRepository floatingPopulationRepository;
    private final DistrictRepository districtRepository;
    private final RestTemplate restTemplate;

    private static final DayOfWeek WEEKDAY = DayOfWeek.WEDNESDAY;
    private static final DayOfWeek WEEKEND = DayOfWeek.SATURDAY;
    private static final int PAGE_SIZE = 1000;

    @Value("${seoul.openapi.key}")
    private String apiKey;

    public void getFloatingPopulation() {
        District district = districtRepository.findByDistrictName("구로구")
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        Map<String, double[]> sensorMap = PopulationSensorLocation.SENSOR_MAP;
        Map<String, Boolean> visited = new HashMap<>();

        int startIndex = 1;

        while (true) {

            int endIndex = startIndex + PAGE_SIZE - 1;

            String url = String.format(
                    "http://openapi.seoul.go.kr:8088/%s/xml/IotVdata018/%d/%d/Guro-gu",
                    apiKey, startIndex, endIndex
            );

            FloatingPopulationResponse response = restTemplate.getForObject(url, FloatingPopulationResponse.class);
            if (response == null || response.getRows() == null) {
                break;
            }

            List<FloatingPopulationRowDto> rows = response.getRows();
            if (rows.isEmpty()) {
                break;
            }

            for (FloatingPopulationRowDto row : rows) {
                if (!sensorMap.containsKey(row.getSerialNo())) continue;

                LocalDateTime sensingTime = parse(row.getSensingTime());
                DayOfWeek day = sensingTime.getDayOfWeek();

                boolean isWeekend;
                if (day == WEEKEND) {
                    isWeekend = true;
                } else if (day == WEEKDAY) {
                    isWeekend = false;
                } else {
                    continue;
                }

                int hour = sensingTime.getHour();

                String key = row.getSerialNo() + "_" + day + "_" + hour;
                if (visited.containsKey(key)) continue;
                visited.put(key, true);

                double[] latLng = sensorMap.get(row.getSerialNo());

                FloatingPopulation population = new FloatingPopulation(
                        district,
                        hour,
                        row.getVisitorCount(),
                        latLng[0],
                        latLng[1],
                        isWeekend
                );

                floatingPopulationRepository.save(population);
            }

            if (rows.size() < PAGE_SIZE) break;
            startIndex += PAGE_SIZE;
        }
    }

    private LocalDateTime parse(String raw) {
        return LocalDateTime.parse(
                raw.replace("_", " "),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }
}
