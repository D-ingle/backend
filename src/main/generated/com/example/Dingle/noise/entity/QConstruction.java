package com.example.Dingle.noise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConstruction is a Querydsl query type for Construction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConstruction extends EntityPathBase<Construction> {

    private static final long serialVersionUID = 1351223810L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConstruction construction = new QConstruction("construction");

    public final com.example.Dingle.district.entity.QDistrict district;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath name = createString("name");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QConstruction(String variable) {
        this(Construction.class, forVariable(variable), INITS);
    }

    public QConstruction(Path<? extends Construction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConstruction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConstruction(PathMetadata metadata, PathInits inits) {
        this(Construction.class, metadata, inits);
    }

    public QConstruction(Class<? extends Construction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

