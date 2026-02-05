package com.example.Dingle.property.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProperty is a Querydsl query type for Property
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProperty extends EntityPathBase<Property> {

    private static final long serialVersionUID = 947697805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProperty property = new QProperty("property");

    public final StringPath address = createString("address");

    public final StringPath apartmentName = createString("apartmentName");

    public final NumberPath<Integer> bathrooms = createNumber("bathrooms", Integer.class);

    public final NumberPath<Integer> bedrooms = createNumber("bedrooms", Integer.class);

    public final QPropertyDeal deal;

    public final com.example.Dingle.district.entity.QDistrict district;

    public final NumberPath<Integer> evParkingSpaces = createNumber("evParkingSpaces", Integer.class);

    public final NumberPath<Double> exclusiveArea = createNumber("exclusiveArea", Double.class);

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final EnumPath<com.example.Dingle.property.dto.OrientationType> orientation = createEnum("orientation", com.example.Dingle.property.dto.OrientationType.class);

    public final NumberPath<Double> parkingRatio = createNumber("parkingRatio", Double.class);

    public final EnumPath<com.example.Dingle.property.type.PropertyType> propertyType = createEnum("propertyType", com.example.Dingle.property.type.PropertyType.class);

    public final com.example.Dingle.realtor.entity.QRealtor realtor;

    public final DateTimePath<java.time.LocalDateTime> registeredAt = createDateTime("registeredAt", java.time.LocalDateTime.class);

    public final QPropertyScore score;

    public final NumberPath<Double> supplyArea = createNumber("supplyArea", Double.class);

    public final NumberPath<Integer> totalFloor = createNumber("totalFloor", Integer.class);

    public QProperty(String variable) {
        this(Property.class, forVariable(variable), INITS);
    }

    public QProperty(Path<? extends Property> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProperty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProperty(PathMetadata metadata, PathInits inits) {
        this(Property.class, metadata, inits);
    }

    public QProperty(Class<? extends Property> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deal = inits.isInitialized("deal") ? new QPropertyDeal(forProperty("deal"), inits.get("deal")) : null;
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
        this.realtor = inits.isInitialized("realtor") ? new com.example.Dingle.realtor.entity.QRealtor(forProperty("realtor")) : null;
        this.score = inits.isInitialized("score") ? new QPropertyScore(forProperty("score"), inits.get("score")) : null;
    }

}

