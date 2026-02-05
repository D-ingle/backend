package com.example.Dingle.environment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEnvironment is a Querydsl query type for Environment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEnvironment extends EntityPathBase<Environment> {

    private static final long serialVersionUID = -13555031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEnvironment environment = new QEnvironment("environment");

    public final com.example.Dingle.district.entity.QDistrict district;

    public final EnumPath<com.example.Dingle.environment.type.EnvironmentType> environmentType = createEnum("environmentType", com.example.Dingle.environment.type.EnvironmentType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath loadAddress = createString("loadAddress");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath name = createString("name");

    public QEnvironment(String variable) {
        this(Environment.class, forVariable(variable), INITS);
    }

    public QEnvironment(Path<? extends Environment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEnvironment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEnvironment(PathMetadata metadata, PathInits inits) {
        this(Environment.class, metadata, inits);
    }

    public QEnvironment(Class<? extends Environment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

