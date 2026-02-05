package com.example.Dingle.property.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPropertyScore is a Querydsl query type for PropertyScore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPropertyScore extends EntityPathBase<PropertyScore> {

    private static final long serialVersionUID = -1508776443L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPropertyScore propertyScore = new QPropertyScore("propertyScore");

    public final NumberPath<Integer> accessibilityScore = createNumber("accessibilityScore", Integer.class);

    public final NumberPath<Integer> convenienceScore = createNumber("convenienceScore", Integer.class);

    public final NumberPath<Integer> environmentScore = createNumber("environmentScore", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> noiseScore = createNumber("noiseScore", Integer.class);

    public final QProperty property;

    public final NumberPath<Long> propertyId = createNumber("propertyId", Long.class);

    public final NumberPath<Integer> safetyScore = createNumber("safetyScore", Integer.class);

    public QPropertyScore(String variable) {
        this(PropertyScore.class, forVariable(variable), INITS);
    }

    public QPropertyScore(Path<? extends PropertyScore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPropertyScore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPropertyScore(PathMetadata metadata, PathInits inits) {
        this(PropertyScore.class, metadata, inits);
    }

    public QPropertyScore(Class<? extends PropertyScore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.property = inits.isInitialized("property") ? new QProperty(forProperty("property"), inits.get("property")) : null;
    }

}

