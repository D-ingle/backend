package com.example.Dingle.safety.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCrimeProneArea is a Querydsl query type for CrimeProneArea
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrimeProneArea extends EntityPathBase<CrimeProneArea> {

    private static final long serialVersionUID = -870188428L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCrimeProneArea crimeProneArea = new QCrimeProneArea("crimeProneArea");

    public final EnumPath<com.example.Dingle.safety.type.CrimeType> crimeType = createEnum("crimeType", com.example.Dingle.safety.type.CrimeType.class);

    public final com.example.Dingle.district.entity.QDistrict district;

    public final ComparablePath<org.locationtech.jts.geom.MultiPolygon> geometry = createComparable("geometry", org.locationtech.jts.geom.MultiPolygon.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> riskLevel = createNumber("riskLevel", Integer.class);

    public QCrimeProneArea(String variable) {
        this(CrimeProneArea.class, forVariable(variable), INITS);
    }

    public QCrimeProneArea(Path<? extends CrimeProneArea> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCrimeProneArea(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCrimeProneArea(PathMetadata metadata, PathInits inits) {
        this(CrimeProneArea.class, metadata, inits);
    }

    public QCrimeProneArea(Class<? extends CrimeProneArea> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

