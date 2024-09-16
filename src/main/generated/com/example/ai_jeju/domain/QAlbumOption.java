package com.example.ai_jeju.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumOption is a Querydsl query type for AlbumOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumOption extends EntityPathBase<AlbumOption> {

    private static final long serialVersionUID = -1823486948L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumOption albumOption = new QAlbumOption("albumOption");

    public final QAlbum album;

    public final NumberPath<Long> albumItemId = createNumber("albumItemId", Long.class);

    public final BooleanPath optionalAm = createBoolean("optionalAm");

    public final BooleanPath optionalDining = createBoolean("optionalDining");

    public final BooleanPath optionalExperience = createBoolean("optionalExperience");

    public final BooleanPath optionalFamily = createBoolean("optionalFamily");

    public final BooleanPath optionalFriend = createBoolean("optionalFriend");

    public final BooleanPath optionalMorning = createBoolean("optionalMorning");

    public final BooleanPath optionalPet = createBoolean("optionalPet");

    public final BooleanPath optionalPlay = createBoolean("optionalPlay");

    public final BooleanPath optionalPm = createBoolean("optionalPm");

    public final BooleanPath optionalSnack = createBoolean("optionalSnack");

    public final BooleanPath optionalStudy = createBoolean("optionalStudy");

    public final BooleanPath optionalWalk = createBoolean("optionalWalk");

    public QAlbumOption(String variable) {
        this(AlbumOption.class, forVariable(variable), INITS);
    }

    public QAlbumOption(Path<? extends AlbumOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumOption(PathMetadata metadata, PathInits inits) {
        this(AlbumOption.class, metadata, inits);
    }

    public QAlbumOption(Class<? extends AlbumOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new QAlbum(forProperty("album"), inits.get("album")) : null;
    }

}

