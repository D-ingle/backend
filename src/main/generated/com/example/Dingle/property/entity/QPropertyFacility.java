package com.example.Dingle.property.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPropertyFacility is a Querydsl query type for PropertyFacility
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPropertyFacility extends EntityPathBase<PropertyFacility> {

    private static final long serialVersionUID = -139547056L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPropertyFacility propertyFacility = new QPropertyFacility("propertyFacility");

    public final QFacility facility;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProperty property;

    public QPropertyFacility(String variable) {
        this(PropertyFacility.class, forVariable(variable), INITS);
    }

    public QPropertyFacility(Path<? extends PropertyFacility> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPropertyFacility(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPropertyFacility(PathMetadata metadata, PathInits inits) {
        this(PropertyFacility.class, metadata, inits);
    }

    public QPropertyFacility(Class<? extends PropertyFacility> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.facility = inits.isInitialized("facility") ? new QFacility(forProperty("facility")) : null;
        this.property = inits.isInitialized("property") ? new QProperty(forProperty("property"), inits.get("property")) : null;
    }

}

