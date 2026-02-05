package com.example.Dingle.district.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDistrict is a Querydsl query type for District
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDistrict extends EntityPathBase<District> {

    private static final long serialVersionUID = 1288360447L;

    public static final QDistrict district = new QDistrict("district");

    public final StringPath districtCode = createString("districtCode");

    public final StringPath districtName = createString("districtName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDistrict(String variable) {
        super(District.class, forVariable(variable));
    }

    public QDistrict(Path<? extends District> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDistrict(PathMetadata metadata) {
        super(District.class, metadata);
    }

}

