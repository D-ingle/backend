package com.example.Dingle.property.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPropertyDeal is a Querydsl query type for PropertyDeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPropertyDeal extends EntityPathBase<PropertyDeal> {

    private static final long serialVersionUID = -1018946919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPropertyDeal propertyDeal = new QPropertyDeal("propertyDeal");

    public final EnumPath<com.example.Dingle.property.type.DealType> dealType = createEnum("dealType", com.example.Dingle.property.type.DealType.class);

    public final NumberPath<Long> deposit = createNumber("deposit", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> monthlyRent = createNumber("monthlyRent", Long.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final QProperty property;

    public QPropertyDeal(String variable) {
        this(PropertyDeal.class, forVariable(variable), INITS);
    }

    public QPropertyDeal(Path<? extends PropertyDeal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPropertyDeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPropertyDeal(PathMetadata metadata, PathInits inits) {
        this(PropertyDeal.class, metadata, inits);
    }

    public QPropertyDeal(Class<? extends PropertyDeal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.property = inits.isInitialized("property") ? new QProperty(forProperty("property"), inits.get("property")) : null;
    }

}

