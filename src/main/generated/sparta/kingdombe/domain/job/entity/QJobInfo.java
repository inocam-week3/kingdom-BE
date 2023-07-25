package sparta.kingdombe.domain.job.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJobInfo is a Querydsl query type for JobInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobInfo extends EntityPathBase<JobInfo> {

    private static final long serialVersionUID = -2004404664L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJobInfo jobInfo = new QJobInfo("jobInfo");

    public final sparta.kingdombe.global.utils.QTimestamped _super = new sparta.kingdombe.global.utils.QTimestamped(this);

    public final StringPath companyname = createString("companyname");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final StringPath logoImage = createString("logoImage");

    public final DateTimePath<java.util.Date> recruitmentEndPeriod = createDateTime("recruitmentEndPeriod", java.util.Date.class);

    public final NumberPath<Long> recruitmentPersonNum = createNumber("recruitmentPersonNum", Long.class);

    public final DateTimePath<java.util.Date> recruitmentStartPeriod = createDateTime("recruitmentStartPeriod", java.util.Date.class);

    public final NumberPath<Long> salary = createNumber("salary", Long.class);

    public final StringPath title = createString("title");

    public final sparta.kingdombe.domain.user.entity.QUser user;

    public final StringPath workInfraImage = createString("workInfraImage");

    public QJobInfo(String variable) {
        this(JobInfo.class, forVariable(variable), INITS);
    }

    public QJobInfo(Path<? extends JobInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJobInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJobInfo(PathMetadata metadata, PathInits inits) {
        this(JobInfo.class, metadata, inits);
    }

    public QJobInfo(Class<? extends JobInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new sparta.kingdombe.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

