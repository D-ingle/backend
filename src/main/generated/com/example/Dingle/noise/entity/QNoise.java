package com.example.Dingle.noise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoise is a Querydsl query type for Noise
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoise extends EntityPathBase<Noise> {

    private static final long serialVersionUID = 1859564297L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoise noise1 = new QNoise("noise1");

    public final com.example.Dingle.district.entity.QDistrict district;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isWeekend = createBoolean("isWeekend");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Double> noise = createNumber("noise", Double.class);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public QNoise(String variable) {
        this(Noise.class, forVariable(variable), INITS);
    }

    public QNoise(Path<? extends Noise> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoise(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoise(PathMetadata metadata, PathInits inits) {
        this(Noise.class, metadata, inits);
    }

    public QNoise(Class<? extends Noise> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

