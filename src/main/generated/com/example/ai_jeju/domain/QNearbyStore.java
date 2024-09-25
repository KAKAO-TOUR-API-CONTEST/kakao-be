package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNearbyStore is a Querydsl query type for NearbyStore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNearbyStore extends EntityPathBase<NearbyStore> {

    private static final long serialVersionUID = -1456390662L;

    public static final QNearbyStore nearbyStore = new QNearbyStore("nearbyStore");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final NumberPath<Long> nearbyStoreId = createNumber("nearbyStoreId", Long.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public QNearbyStore(String variable) {
        super(NearbyStore.class, forVariable(variable));
    }

    public QNearbyStore(Path<? extends NearbyStore> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNearbyStore(PathMetadata metadata) {
        super(NearbyStore.class, metadata);
    }

}

