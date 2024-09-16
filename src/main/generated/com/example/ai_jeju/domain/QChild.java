package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChild is a Querydsl query type for Child
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChild extends EntityPathBase<Child> {

    private static final long serialVersionUID = 1561902964L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChild child = new QChild("child");

    public final StringPath birthDate = createString("birthDate");

    public final NumberPath<Long> childId = createNumber("childId", Long.class);

    public final StringPath childName = createString("childName");

    public final StringPath childProfile = createString("childProfile");

    public final BooleanPath gender = createBoolean("gender");

    public final StringPath realtion = createString("realtion");

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final QUser user;

    public QChild(String variable) {
        this(Child.class, forVariable(variable), INITS);
    }

    public QChild(Path<? extends Child> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChild(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChild(PathMetadata metadata, PathInits inits) {
        this(Child.class, metadata, inits);
    }

    public QChild(Class<? extends Child> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

