package com.example.Dingle.safety.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSafety is a Querydsl query type for Safety
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSafety extends EntityPathBase<Safety> {

    private static final long serialVersionUID = -1166085305L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSafety safety = new QSafety("safety");

    public final com.example.Dingle.district.entity.QDistrict district;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.example.Dingle.infra.type.InfraType> infraType = createEnum("infraType", com.example.Dingle.infra.type.InfraType.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public QSafety(String variable) {
        this(Safety.class, forVariable(variable), INITS);
    }

    public QSafety(Path<? extends Safety> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSafety(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSafety(PathMetadata metadata, PathInits inits) {
        this(Safety.class, metadata, inits);
    }

    public QSafety(Class<? extends Safety> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

