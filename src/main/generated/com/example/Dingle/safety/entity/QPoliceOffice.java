package com.example.Dingle.safety.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPoliceOffice is a Querydsl query type for PoliceOffice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPoliceOffice extends EntityPathBase<PoliceOffice> {

    private static final long serialVersionUID = 922348079L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPoliceOffice policeOffice = new QPoliceOffice("policeOffice");

    public final StringPath address = createString("address");

    public final com.example.Dingle.district.entity.QDistrict district;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public QPoliceOffice(String variable) {
        this(PoliceOffice.class, forVariable(variable), INITS);
    }

    public QPoliceOffice(Path<? extends PoliceOffice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPoliceOffice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPoliceOffice(PathMetadata metadata, PathInits inits) {
        this(PoliceOffice.class, metadata, inits);
    }

    public QPoliceOffice(Class<? extends PoliceOffice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.district = inits.isInitialized("district") ? new com.example.Dingle.district.entity.QDistrict(forProperty("district")) : null;
    }

}

