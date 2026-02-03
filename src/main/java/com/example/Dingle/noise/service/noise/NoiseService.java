package com.example.Dingle.noise.service.noise;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.noise.dto.noise.NoiseResponse;
import com.example.Dingle.noise.dto.noise.NoiseRowDTO;
import com.example.Dingle.noise.entity.Noise;
import com.example.Dingle.noise.repository.NoiseRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class NoiseService {

    private final NoiseRepository noiseRepository;
    private final DistrictRepository districtRepository;
    private final RestTemplate restTemplate;

    private static final DayOfWeek WEEKDAY = DayOfWeek.WEDNESDAY;
    private static final DayOfWeek WEEKEND = DayOfWeek.SATURDAY;

    @Value("${seoul.openapi.key}")
    private String apiKey;

    public void getNoise(LocalDate date) {

        String url = String.format(
                "http://openapi.seoul.go.kr:8088/%s/xml/IotVdata017/1/1000/Guro-gu/%s", apiKey, date);

        NoiseResponse response = restTemplate.getForObject(url, NoiseResponse.class);

        if (response == null || response.getRows() == null) return;

        District district = districtRepository.findByDistrictName("구로구")
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        Map<String, double[]> sensorMap = NoiseSensorLocation.SENSOR_MAP;

        for (NoiseRowDTO row : response.getRows()) {

            if (!sensorMap.containsKey(row.getSn())) continue;
            if (row.getAvgNoise() == null) continue;

            LocalDateTime measuredAt = parse(row.getMeasuredAt());
            DayOfWeek day = measuredAt.getDayOfWeek();

            boolean isWeekend;
            if (day == WEEKEND) {
                isWeekend = true;
            } else if (day == WEEKDAY) {
                isWeekend = false;
            } else {
                continue;
            }

            int hour = measuredAt.getHour();

            double[] latLng = sensorMap.get(row.getSn());

            Noise noise = new Noise(
                    district,
                    hour,
                    row.getAvgNoise(),
                    latLng[0],
                    latLng[1],
                    isWeekend
            );

            noiseRepository.save(noise);
        }
    }

    private LocalDateTime parse(String raw) {
        return LocalDateTime.parse(
                raw.replace("_", " "),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }
}
