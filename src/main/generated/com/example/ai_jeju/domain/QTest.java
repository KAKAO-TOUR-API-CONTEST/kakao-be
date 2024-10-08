package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTest is a Querydsl query type for Test
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTest extends EntityPathBase<Test> {

    private static final long serialVersionUID = 743624506L;

    public static final QTest test = new QTest("test");

    public final StringPath address = createString("address");

    public final BooleanPath babySpareChair = createBoolean("babySpareChair");

    public final StringPath breakTime = createString("breakTime");

    public final NumberPath<Integer> categoryId = createNumber("categoryId", Integer.class);

    public final StringPath imgSrc = createString("imgSrc");

    public final NumberPath<Double> mapX = createNumber("mapX", Double.class);

    public final NumberPath<Double> mapY = createNumber("mapY", Double.class);

    public final StringPath name = createString("name");

    public final StringPath nokidsdetail = createString("nokidsdetail");

    public final StringPath noKidsZone = createString("noKidsZone");

    public final StringPath operationTime = createString("operationTime");

    public final BooleanPath parking = createBoolean("parking");

    public final BooleanPath pet = createBoolean("pet");

    public final BooleanPath playground = createBoolean("playground");

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final BooleanPath stroller = createBoolean("stroller");

    public final BooleanPath strollerVal = createBoolean("strollerVal");

    public final StringPath tel = createString("tel");

    public QTest(String variable) {
        super(Test.class, forVariable(variable));
    }

    public QTest(Path<? extends Test> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTest(PathMetadata metadata) {
        super(Test.class, metadata);
    }

}

