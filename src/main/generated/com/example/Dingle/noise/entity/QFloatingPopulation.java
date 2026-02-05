package com.example.Dingle.noise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFloatingPopulation is a Querydsl query type for FloatingPopulation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFloatingPopulation extends EntityPathBase<FloatingPopulation> {

    private static final long serialVersionUID = 124491876L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFloatingPopulation floatingPopulation = new QFloatingPopulation("floatingPopulation");

    public final com.example.Dingle.district.entity.QDistrict district;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isWeekend = createBoolean("isWeekend");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Integer> population = createNumber("population", Integer.class);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public QFloatingPopulation(String variable) {
        this(FloatingPopulation.class, forVariable(variable), INITS);
    }

    public QFloatingPopulation(Path<? extends FloatingPopulation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFloatingPopulation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFloatingPopulation(PathMetadata metadata, PathInits inits) {
        this(FloatingPopulation.class, metadata, inits);
    }

    public QFloatingPopulation(Class<? extends FloatingPopulation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

