package com.example.Dingle.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSavedProperty is a Querydsl query type for SavedProperty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSavedProperty extends EntityPathBase<SavedProperty> {

    private static final long serialVersionUID = 1768040078L;

    public static final QSavedProperty savedProperty = new QSavedProperty("savedProperty");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> propertyId = createNumber("propertyId", Long.class);

    public final StringPath userId = createString("userId");

    public QSavedProperty(String variable) {
        super(SavedProperty.class, forVariable(variable));
    }

    public QSavedProperty(Path<? extends SavedProperty> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSavedProperty(PathMetadata metadata) {
        super(SavedProperty.class, metadata);
    }

}

