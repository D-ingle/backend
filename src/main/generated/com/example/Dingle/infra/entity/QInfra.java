package com.example.Dingle.infra.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInfra is a Querydsl query type for Infra
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInfra extends EntityPathBase<Infra> {

    private static final long serialVersionUID = -1056683319L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInfra infra = new QInfra("infra");

    public final EnumPath<com.example.Dingle.infra.type.Category> category = createEnum("category", com.example.Dingle.infra.type.Category.class);

    public final com.example.Dingle.district.entity.QDistrict district;

    public final StringPath hospitalType = createString("hospitalType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.example.Dingle.infra.type.InfraType> infraType = createEnum("infraType", com.example.Dingle.infra.type.InfraType.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath name = createString("name");

    public final StringPath roadAddress = createString("roadAddress");

    public QInfra(String variable) {
        this(Infra.class, forVariable(variable), INITS);
    }

    public QInfra(Path<? extends Infra> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInfra(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInfra(PathMetadata metadata, PathInits inits) {
        this(Infra.class, metadata, inits);
    }

    public QInfra(Class<? extends Infra> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

