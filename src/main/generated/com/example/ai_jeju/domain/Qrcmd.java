package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * Qrcmd is a Querydsl query type for rcmd
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qrcmd extends EntityPathBase<rcmd> {

    private static final long serialVersionUID = 744516112L;

    public static final Qrcmd rcmd = new Qrcmd("rcmd");

    public final NumberPath<Long> rcmdId = createNumber("rcmdId", Long.class);

    public Qrcmd(String variable) {
        super(rcmd.class, forVariable(variable));
    }

    public Qrcmd(Path<? extends rcmd> path) {
        super(path.getType(), path.getMetadata());
    }

    public Qrcmd(PathMetadata metadata) {
        super(rcmd.class, metadata);
    }

}

