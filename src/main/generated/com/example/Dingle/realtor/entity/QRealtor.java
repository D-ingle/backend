package com.example.Dingle.realtor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRealtor is a Querydsl query type for Realtor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRealtor extends EntityPathBase<Realtor> {

    private static final long serialVersionUID = 1148776425L;

    public static final QRealtor realtor = new QRealtor("realtor");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath licenseNumber = createString("licenseNumber");

    public final StringPath officeAddress = createString("officeAddress");

    public final StringPath officeName = createString("officeName");

    public final StringPath officePhone = createString("officePhone");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath username = createString("username");

    public QRealtor(String variable) {
        super(Realtor.class, forVariable(variable));
    }

    public QRealtor(Path<? extends Realtor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRealtor(PathMetadata metadata) {
        super(Realtor.class, metadata);
    }

}

