package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTest2 is a Querydsl query type for Test2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTest2 extends EntityPathBase<Test2> {

    private static final long serialVersionUID = 1577523256L;

    public static final QTest2 test2 = new QTest2("test2");

    public final StringPath address = createString("address");

    public final BooleanPath babySpareChair = createBoolean("babySpareChair");

    public final NumberPath<Integer> categoryId = createNumber("categoryId", Integer.class);

    public final StringPath imgSrc = createString("imgSrc");

    public final NumberPath<Double> mapX = createNumber("mapX", Double.class);

    public final NumberPath<Double> mapY = createNumber("mapY", Double.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> nearbyStoreId = createNumber("nearbyStoreId", Long.class);

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

    public QTest2(String variable) {
        super(Test2.class, forVariable(variable));
    }

    public QTest2(Path<? extends Test2> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTest2(PathMetadata metadata) {
        super(Test2.class, metadata);
    }

}

