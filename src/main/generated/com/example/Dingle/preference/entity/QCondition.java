package com.example.Dingle.preference.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCondition is a Querydsl query type for Condition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCondition extends EntityPathBase<Condition> {

    private static final long serialVersionUID = -1039355747L;

    public static final QCondition condition = new QCondition("condition");

    public final StringPath conditionName = createString("conditionName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCondition(String variable) {
        super(Condition.class, forVariable(variable));
    }

    public QCondition(Path<? extends Condition> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCondition(PathMetadata metadata) {
        super(Condition.class, metadata);
    }

}

