package sparta.kingdombe.domain.job.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJobInfo is a Querydsl query type for JobInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobInfo extends EntityPathBase<JobInfo> {

    private static final long serialVersionUID = -2004404664L;

    public static final QJobInfo jobInfo = new QJobInfo("jobInfo");

    public final sparta.kingdombe.global.utils.QTimestamped _super = new sparta.kingdombe.global.utils.QTimestamped(this);

    public final StringPath companyName = createString("companyName");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath images = createString("images");

    public final StringPath location = createString("location");

    public final StringPath managerEmail = createString("managerEmail");

    public final StringPath managerName = createString("managerName");

    public final DateTimePath<java.util.Date> recruitmentPeriod = createDateTime("recruitmentPeriod", java.util.Date.class);

    public final NumberPath<Long> recruitmentPersonNum = createNumber("recruitmentPersonNum", Long.class);

    public final NumberPath<Long> salary = createNumber("salary", Long.class);

    public final StringPath title = createString("title");

    public QJobInfo(String variable) {
        super(JobInfo.class, forVariable(variable));
    }

    public QJobInfo(Path<? extends JobInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJobInfo(PathMetadata metadata) {
        super(JobInfo.class, metadata);
    }

}

