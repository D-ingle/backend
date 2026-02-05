package com.example.Dingle.property.repository;

import com.example.Dingle.property.dto.PropertySearchFilterDTO;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.entity.QProperty;
import com.example.Dingle.property.entity.QPropertyDeal;
import com.example.Dingle.property.entity.QPropertyScore;
import com.example.Dingle.property.type.DealType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PropertyScoreRepositoryImpl implements PropertyScoreRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PropertyScore> findListForMain(PropertySearchFilterDTO filter, List<Long> districtIds) {
        QPropertyScore ps = QPropertyScore.propertyScore;
        QProperty p = QProperty.property;
        QPropertyDeal d = QPropertyDeal.propertyDeal;

        BooleanBuilder where = new BooleanBuilder();

        if(filter.getPropertyType() != null){
            where.and(p.propertyType.eq(filter.getPropertyType()));
        }

        List<Long> districtIdList = (districtIds != null && !districtIds.isEmpty()) ? districtIds : filter.getDistrictIds();

        if(districtIdList != null && !districtIdList.isEmpty()) {
            where.and(p.district.id.in(districtIdList));
        }

        String keyword = (filter.getKeyword() == null) ? null : filter.getKeyword().trim();
        if(keyword != null && !keyword.isBlank()){
            where.and(
                    p.apartmentName.containsIgnoreCase(keyword)
                            .or(p.district.districtName.containsIgnoreCase(keyword)));
        }

        if (filter.getMinExclusiveArea() != null) where.and(p.exclusiveArea.goe(filter.getMinExclusiveArea()));
        if (filter.getMaxExclusiveArea() != null) where.and(p.exclusiveArea.loe(filter.getMaxExclusiveArea()));

        BooleanBuilder dealWhere = new BooleanBuilder();
        if(filter.getDealType() != null){
            dealWhere.and(d.dealType.eq(filter.getDealType()));

            if(filter.getDealType() == DealType.RENT) {
                if(filter.getMinDeposit() != null) dealWhere.and(d.deposit.goe(filter.getMinDeposit()));
                if(filter.getMaxDeposit() != null) dealWhere.and(d.deposit.loe(filter.getMaxDeposit()));
                if(filter.getMinMonthlyRent() != null) dealWhere.and(d.monthlyRent.goe(filter.getMinMonthlyRent()));
                if(filter.getMaxMonthlyRent() != null) dealWhere.and(d.monthlyRent.loe(filter.getMaxMonthlyRent()));
            }

            if(filter.getDealType() == DealType.LEASE || filter.getDealType() == DealType.SALE) {
                if(filter.getMinPrice() != null) dealWhere.and(d.price.goe(filter.getMinPrice()));
                if(filter.getMaxPrice() != null) dealWhere.and(d.price.loe(filter.getMaxPrice()));
            }
        }

        return queryFactory
                .select(ps)
                .from(ps)
                .join(ps.property, p)
                .join(p.deal, d)
                .where(where)
                .where(where, dealWhere)
                .fetch();
    }
}
