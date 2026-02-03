package com.example.Dingle.noise.service.firestation;

import com.example.Dingle.noise.dto.firestation.FireStationLocationDTO;
import com.example.Dingle.noise.entity.FireStation;
import com.example.Dingle.noise.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveService {

    private final FireStationService fireStationService;
    private final FireStationRepository fireStationRepository;

    @Transactional
    public void saveFireStation() {

        List<FireStationLocationDTO> fireStationLocations = fireStationService.getFireStationLocations();

        List<FireStation> fireStations = fireStationLocations.stream()
                .map(dto -> new FireStation(
                        dto.getName(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();
        fireStationRepository.saveAll(fireStations);
    }

}
