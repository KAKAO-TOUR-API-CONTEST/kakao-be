package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumItem is a Querydsl query type for AlbumItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumItem extends EntityPathBase<AlbumItem> {

    private static final long serialVersionUID = -1347322758L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumItem albumItem = new QAlbumItem("albumItem");

    public final QAlbum album;

    public final NumberPath<Long> albumItemId = createNumber("albumItemId", Long.class);

    public final StringPath imgSrc = createString("imgSrc");

    public QAlbumItem(String variable) {
        this(AlbumItem.class, forVariable(variable), INITS);
    }

    public QAlbumItem(Path<? extends AlbumItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumItem(PathMetadata metadata, PathInits inits) {
        this(AlbumItem.class, metadata, inits);
    }

    public QAlbumItem(Class<? extends AlbumItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new QAlbum(forProperty("album"), inits.get("album")) : null;
    }

}

