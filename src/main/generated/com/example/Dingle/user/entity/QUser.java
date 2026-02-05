package com.example.Dingle.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 555721337L;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath destinationAddress = createString("destinationAddress");

    public final StringPath destinationName = createString("destinationName");

    public final DateTimePath<java.time.LocalDateTime> destinationUpdatedAt = createDateTime("destinationUpdatedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> onboardedAt = createDateTime("onboardedAt", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<com.example.Dingle.property.type.PropertyType> propertyType = createEnum("propertyType", com.example.Dingle.property.type.PropertyType.class);

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public final EnumPath<com.example.Dingle.user.type.UserRole> userRole = createEnum("userRole", com.example.Dingle.user.type.UserRole.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

