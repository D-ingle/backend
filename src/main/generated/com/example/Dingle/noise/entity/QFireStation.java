package com.example.Dingle.noise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFireStation is a Querydsl query type for FireStation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFireStation extends EntityPathBase<FireStation> {

    private static final long serialVersionUID = -2006649139L;

    public static final QFireStation fireStation = new QFireStation("fireStation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath name = createString("name");

    public QFireStation(String variable) {
        super(FireStation.class, forVariable(variable));
    }

    public QFireStation(Path<? extends FireStation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFireStation(PathMetadata metadata) {
        super(FireStation.class, metadata);
    }

}

