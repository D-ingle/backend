package com.example.Dingle.onboarding.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPreferredDistrict is a Querydsl query type for PreferredDistrict
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPreferredDistrict extends EntityPathBase<PreferredDistrict> {

    private static final long serialVersionUID = -1194548367L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPreferredDistrict preferredDistrict = new QPreferredDistrict("preferredDistrict");

    public final com.example.Dingle.district.entity.QDistrict district;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.Dingle.user.entity.QUser user;

    public QPreferredDistrict(String variable) {
        this(PreferredDistrict.class, forVariable(variable), INITS);
    }

    public QPreferredDistrict(Path<? extends PreferredDistrict> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPreferredDistrict(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPreferredDistrict(PathMetadata metadata, PathInits inits) {
        this(PreferredDistrict.class, metadata, inits);
    }

    public QPreferredDistrict(Class<? extends PreferredDistrict> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
        this.user = inits.isInitialized("user") ? new com.example.Dingle.user.entity.QUser(forProperty("user")) : null;
    }

}

