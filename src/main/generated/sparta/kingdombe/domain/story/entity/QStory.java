package sparta.kingdombe.domain.story.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStory is a Querydsl query type for Story
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStory extends EntityPathBase<Story> {

    private static final long serialVersionUID = 993022074L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStory story = new QStory("story");

    public final sparta.kingdombe.global.utils.QTimestamped _super = new sparta.kingdombe.global.utils.QTimestamped(this);

    public final ListPath<sparta.kingdombe.domain.comment.entity.Comment, sparta.kingdombe.domain.comment.entity.QComment> commentList = this.<sparta.kingdombe.domain.comment.entity.Comment, sparta.kingdombe.domain.comment.entity.QComment>createList("commentList", sparta.kingdombe.domain.comment.entity.Comment.class, sparta.kingdombe.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final NumberPath<Long> liked = createNumber("liked", Long.class);

    public final StringPath title = createString("title");

    public final sparta.kingdombe.domain.user.entity.QUser user;

    public QStory(String variable) {
        this(Story.class, forVariable(variable), INITS);
    }

    public QStory(Path<? extends Story> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStory(PathMetadata metadata, PathInits inits) {
        this(Story.class, metadata, inits);
    }

    public QStory(Class<? extends Story> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new sparta.kingdombe.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

