package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleItem is a Querydsl query type for ScheduleItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleItem extends EntityPathBase<ScheduleItem> {

    private static final long serialVersionUID = -971259662L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleItem scheduleItem = new QScheduleItem("scheduleItem");

    public final QChild child;

    public final StringPath day = createString("day");

    public final StringPath month = createString("month");

    public final NumberPath<Long> scheduleItemId = createNumber("scheduleItemId", Long.class);

    public final StringPath year = createString("year");

    public QScheduleItem(String variable) {
        this(ScheduleItem.class, forVariable(variable), INITS);
    }

    public QScheduleItem(Path<? extends ScheduleItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleItem(PathMetadata metadata, PathInits inits) {
        this(ScheduleItem.class, metadata, inits);
    }

    public QScheduleItem(Class<? extends ScheduleItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.child = inits.isInitialized("child") ? new QChild(forProperty("child"), inits.get("child")) : null;
    }

}

