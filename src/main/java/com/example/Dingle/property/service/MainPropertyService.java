package com.example.Dingle.property.service;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.onboarding.repository.PreferredDistrictRepository;
import com.example.Dingle.property.dto.*;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyImage;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyImageRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.type.ImageType;
import com.example.Dingle.property.type.PropertyType;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.SavedPropertyRepository;
import com.example.Dingle.user.repository.UserRepository;
import com.example.Dingle.util.score.ConditionScoreExtractor;
import com.example.Dingle.util.score.PropertyScoreMapper;
import com.example.Dingle.util.score.ScoreCalc;
import com.example.Dingle.util.dto.PreferredConditionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@Service
public class MainPropertyService {

    private static final List<Long> DEFAULT_DISTRICT_IDS = List.of(1L, 2L, 3L);
    private static final List<Long> DEFAULT_CONDITION_IDS = List.of(1L, 2L, 3L);
    private static final List<Long> ALL_CONDITION_IDS = List.of(1L, 2L, 3L, 4L, 5L);

    private final UserRepository userRepository;
    private final PreferredDistrictRepository preferredDistrictRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;
    private final SavedPropertyRepository savedPropertyRepository;
    private final PropertyImageRepository propertyImageRepository;

    public MainPropertyService(UserRepository userRepository, PreferredDistrictRepository preferredDistrictRepository, PropertyRepository propertyRepository, PropertyScoreRepository propertyScoreRepository, SavedPropertyRepository savedPropertyRepository, PropertyImageRepository propertyImageRepository) {
        this.userRepository = userRepository;
        this.preferredDistrictRepository = preferredDistrictRepository;
        this.propertyRepository = propertyRepository;
        this.propertyScoreRepository = propertyScoreRepository;
        this.savedPropertyRepository = savedPropertyRepository;
        this.propertyImageRepository = propertyImageRepository;
    }

    public MainPropertyResponseDTO getMainProperty(String userId, List<Long> selectConditions, PropertyType propertyType, Long cursor, Long size) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.USER_NOT_EXISTS));

        List<Long> districtIds = preferredDistrictRepository.findPreferredDistrictByUserPk(user.getId());
        if (districtIds.isEmpty()) districtIds = DEFAULT_DISTRICT_IDS;

        PropertySearchFilterDTO filter = PropertySearchFilterDTO.builder()
                .keyword(null)
                .propertyType(propertyType)
                .build();

        return getPropertyByScoreRanking(user, districtIds, filter, selectConditions, cursor, size);
    }

    public MainPropertyResponseDTO searchProperty(String userId, PropertySearchRequestDTO request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.USER_NOT_EXISTS));

        List<Long> onboardingDistrictIds = preferredDistrictRepository.findPreferredDistrictByUserPk(user.getId());
        if (onboardingDistrictIds.isEmpty()) onboardingDistrictIds = DEFAULT_DISTRICT_IDS;

        List<Long> districtIds;
        if(request.getDistrictIds() == null) districtIds = onboardingDistrictIds;
        else if (request.getDistrictIds().isEmpty()) districtIds = null;
        else districtIds = request.getDistrictIds();

        PropertySearchFilterDTO filter = PropertySearchFilterDTO.builder()
                .keyword(request.getKeyword())
                .propertyType(request.getPropertyType())
                .districtIds(districtIds)
                .dealType(request.getDealType())
                .minPrice(request.getMinPrice())
                .maxPrice(request.getMaxPrice())
                .minDeposit(request.getMinDeposit())
                .maxDeposit(request.getMaxDeposit())
                .minMonthlyRent(request.getMinMonthlyRent())
                .maxMonthlyRent(request.getMaxMonthlyRent())
                .minExclusiveArea(request.getMinExclusiveArea())
                .maxExclusiveArea(request.getMaxExclusiveArea())
                .build();

        return getPropertyByScoreRanking(user, null, filter, request.getSelectConditions(), request.getCursor(), request.getSize());
    }


    private MainPropertyResponseDTO getPropertyByScoreRanking(User user, List<Long> districtIds, PropertySearchFilterDTO filter, List<Long> selectConditions, Long cursor, Long size) {
        List<PreferredConditionDTO> conditions = new ArrayList<>();
        for (int i = 0; i < Math.min(selectConditions.size(), 3); i++) {
            conditions.add(new PreferredConditionDTO(selectConditions.get(i), i + 1));
        }
        if (conditions.isEmpty()) {
            conditions = IntStream.range(0, DEFAULT_CONDITION_IDS.size())
                    .mapToObj(i -> new PreferredConditionDTO(
                            DEFAULT_CONDITION_IDS.get(i),
                            i + 1
                    ))
                    .toList();
        }

        Set<Long> conditionIds = conditions.stream()
                .map(PreferredConditionDTO::getConditionId)
                .collect(Collectors.toSet());

        List<PropertyScore> scoreData = propertyScoreRepository.findListForMain(filter, districtIds);

        final List<PreferredConditionDTO> prefer = conditions;
        List<CalculatedScoreDTO> rankedList = scoreData.stream()
                .map(entity -> {
                    PropertyScoreDTO dto = PropertyScore.fromEntity(entity);
                    double score = ScoreCalc.calculate(dto, prefer);
                    return new CalculatedScoreDTO(dto.getPropertyId(), score);
                })
                .sorted(Comparator.comparing(CalculatedScoreDTO::getScore).reversed())
                .toList();

        int start = (cursor == null) ? 0 : cursor.intValue();
        int pageSize = (size == null || size <= 0) ? 20 : size.intValue();
        int end = Math.min(start + pageSize, rankedList.size());

        if (start >= rankedList.size()) {
            return new MainPropertyResponseDTO(Collections.emptyList(), null, false);
        }

        List<CalculatedScoreDTO> pagedList = rankedList.subList(start, end);
        List<Long> pagePropertyIds = pagedList.stream().map(CalculatedScoreDTO::getPropertyId).toList();

        List<Property> properties = propertyRepository.findAllByIdInWithDetails(pagePropertyIds);

        Map<Long, Property> propertyMap = properties.stream()
                .collect(Collectors.toMap( Property::getId, Function.identity() ));

        List<PropertyImage> images = propertyImageRepository.findAllByProperty_IdInAndImageTypeOrderByProperty_IdAsc(pagePropertyIds, ImageType.PROPERTY);
        Map<Long, String> mainImage = new HashMap<>();
        for(PropertyImage image : images) {
            Long id = image.getProperty().getId();
            mainImage.put(id, image.getImageUrl());
        }

        List<MainPropertyResponseDTO.PropertyItem> items = pagedList.stream()
                .map(cs -> {
                    Property property = propertyMap.get(cs.getPropertyId());

                    if(property == null) return null;

                    boolean isLiked = savedPropertyRepository.existsByUserIdAndPropertyId(user.getId().toString(), property.getId());

                    return MainPropertyResponseDTO.PropertyItem.builder()
                            .propertyId(property.getId())
                            .imageUrl(mainImage.get(property.getId()))
                            .propertyType(property.getPropertyType())
                            .apartmentName(property.getApartmentName())
                            .exclusiveArea(property.getExclusiveArea())
                            .supplyArea(property.getSupplyArea())
                            .floor(property.getFloor())
                            .totalFloor(property.getTotalFloor())
                            .liked(isLiked)
                            .latitude(property.getLatitude())
                            .longitude(property.getLongitude())
                            .dealInfo(buildDealInfo(property))
                            .conditions(buildConditionInfo(property, conditionIds))
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        Long nextCursor = (end < rankedList.size()) ? (long) end : null;
        boolean hasNext = nextCursor != null;

        return new MainPropertyResponseDTO(items, nextCursor, hasNext);
    }

    private MainPropertyResponseDTO.DealInfo buildDealInfo(Property property) {
        return switch (property.getDeal().getDealType()){
            case SALE, LEASE -> new MainPropertyResponseDTO.DealInfo(
                    property.getDeal().getDealType(),
                    property.getDeal().getPrice(),
                    null,
                    null
            );
            case RENT -> new MainPropertyResponseDTO.DealInfo(
                    property.getDeal().getDealType(),
                    null,
                    property.getDeal().getDeposit(),
                    property.getDeal().getMonthlyRent()
            );
        };
    }

    private List<Long> buildConditionInfo(Property property, Set<Long> conditionIds) {
        PropertyScoreDTO propertyScoreDTO = PropertyScoreMapper.from(property);

        List<Long> top3ConditionsIds = ALL_CONDITION_IDS.stream()
                .sorted(
                        Comparator.comparingInt((Long id) -> ConditionScoreExtractor.getScore(propertyScoreDTO, id)).reversed()
                                .thenComparingLong(Long::longValue)
                )
                .limit(3)
                .toList();

        return top3ConditionsIds.stream()
                .filter(conditionIds::contains)
                .toList();
    }
}