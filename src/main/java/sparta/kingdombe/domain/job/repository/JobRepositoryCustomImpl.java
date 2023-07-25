package sparta.kingdombe.domain.job.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sparta.kingdombe.domain.job.dto.JobResponseDto;
import sparta.kingdombe.domain.job.dto.JobSearchCondition;
import sparta.kingdombe.domain.job.entity.JobInfo;
import sparta.kingdombe.domain.job.entity.QJobInfo;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sparta.kingdombe.domain.job.entity.QJobInfo.*;
import static sparta.kingdombe.domain.story.entity.QStory.story;

@RequiredArgsConstructor
public class JobRepositoryCustomImpl implements JobRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public Page<JobInfo> searchJob(JobSearchCondition condition, Pageable pageable) {

        List<JobInfo> content = query
                .selectFrom(jobInfo)
                .where(
                        titleLike(condition.getTitle()),
                        locationEq(condition.getLocation()),
                        salaryGoe(condition.getSalary()),
                        personNumGoe(condition.getRecruitpersonnum()))
                .orderBy(jobInfo.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query
                .selectFrom(jobInfo)
                .where(
                        titleLike(condition.getTitle()),
                        locationEq(condition.getLocation()),
                        salaryGoe(condition.getSalary()),
                        personNumGoe(condition.getRecruitpersonnum()))
                .fetch()
                .size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleLike(String titleCond) {
        return hasText(titleCond) ? jobInfo.title.like("%" + titleCond + "%") : null;
    }

    private BooleanExpression locationEq(String locationCond) {
        return hasText(locationCond) ? jobInfo.location.eq(locationCond) : null;
    }

    private BooleanExpression salaryGoe(Long salaryCond) {
        return hasText(String.valueOf(salaryCond)) ? jobInfo.salary.goe(salaryCond) : null;
    }

    private BooleanExpression personNumGoe(Long personNumCond) {
        return hasText(String.valueOf(personNumCond)) ? jobInfo.recruitmentPersonNum.goe(personNumCond) : null;
    }
}
