package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmergency is a Querydsl query type for Emergency
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmergency extends EntityPathBase<Emergency> {

    private static final long serialVersionUID = 32547817L;

    public static final QEmergency emergency = new QEmergency("emergency");

    public final StringPath address = createString("address");

    public final StringPath breaktime = createString("breaktime");

    public final StringPath category = createString("category");

    public final NumberPath<Long> emergencyid = createNumber("emergencyid", Long.class);

    public final StringPath imgsrc = createString("imgsrc");

    public final NumberPath<Double> mapx = createNumber("mapx", Double.class);

    public final NumberPath<Double> mapy = createNumber("mapy", Double.class);

    public final StringPath operationtime = createString("operationtime");

    public final StringPath pharname = createString("pharname");

    public final StringPath tel = createString("tel");

    public QEmergency(String variable) {
        super(Emergency.class, forVariable(variable));
    }

    public QEmergency(Path<? extends Emergency> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmergency(PathMetadata metadata) {
        super(Emergency.class, metadata);
    }

}

