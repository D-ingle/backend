package com.example.Dingle.property.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPropertyOption is a Querydsl query type for PropertyOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPropertyOption extends EntityPathBase<PropertyOption> {

    private static final long serialVersionUID = 370200418L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPropertyOption propertyOption = new QPropertyOption("propertyOption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOption option;

    public final QProperty property;

    public QPropertyOption(String variable) {
        this(PropertyOption.class, forVariable(variable), INITS);
    }

    public QPropertyOption(Path<? extends PropertyOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPropertyOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPropertyOption(PathMetadata metadata, PathInits inits) {
        this(PropertyOption.class, metadata, inits);
    }

    public QPropertyOption(Class<? extends PropertyOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.option = inits.isInitialized("option") ? new QOption(forProperty("option")) : null;
        this.property = inits.isInitialized("property") ? new QProperty(forProperty("property"), inits.get("property")) : null;
    }

}

