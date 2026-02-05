package com.example.Dingle.onboarding.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPreferredCondition is a Querydsl query type for PreferredCondition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPreferredCondition extends EntityPathBase<PreferredCondition> {

    private static final long serialVersionUID = 394525080L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPreferredCondition preferredCondition = new QPreferredCondition("preferredCondition");

    public final com.example.Dingle.preference.entity.QCondition condition;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public final com.example.Dingle.user.entity.QUser user;

    public QPreferredCondition(String variable) {
        this(PreferredCondition.class, forVariable(variable), INITS);
    }

    public QPreferredCondition(Path<? extends PreferredCondition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPreferredCondition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPreferredCondition(PathMetadata metadata, PathInits inits) {
        this(PreferredCondition.class, metadata, inits);
    }

    public QPreferredCondition(Class<? extends PreferredCondition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.condition = inits.isInitialized("condition") ? new com.example.Dingle.preference.entity.QCondition(forProperty("condition")) : null;
        this.user = inits.isInitialized("user") ? new com.example.Dingle.user.entity.QUser(forProperty("user")) : null;
    }

}

