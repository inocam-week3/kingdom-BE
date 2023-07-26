package sparta.kingdombe.domain.job.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sparta.kingdombe.domain.job.dto.JobAllResponseDto;
import sparta.kingdombe.domain.job.dto.JobResponseDto;
import sparta.kingdombe.domain.job.dto.JobSearchCondition;
import sparta.kingdombe.domain.job.dto.QJobAllResponseDto;
import sparta.kingdombe.domain.job.entity.JobInfo;
import sparta.kingdombe.domain.job.entity.QJobInfo;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;
import static sparta.kingdombe.domain.job.entity.QJobInfo.*;
import static sparta.kingdombe.domain.story.entity.QStory.story;

@RequiredArgsConstructor
@Slf4j
public class JobRepositoryCustomImpl implements JobRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public Page<JobAllResponseDto> searchJob(JobSearchCondition condition, Pageable pageable) {

        log.info("쿼리 실행 전");

        List<JobAllResponseDto> content = query
                .select(new QJobAllResponseDto(
                        jobInfo.id,
                        jobInfo.companyname,
                        jobInfo.title,
                        jobInfo.location,
                        jobInfo.recruitmentStartPeriod,
                        jobInfo.recruitmentEndPeriod,
                        jobInfo.salary,
                        jobInfo.createdAt
                ))
                .from(jobInfo)
                .where(
                        titleLike(condition.getTitle()),
                        locationEq(condition.getLocation()),
                        salaryGoe(condition.getSalary()))
//                        personNumGoe(condition.getRecruitpersonnum()))
                .orderBy(jobInfo.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query
                .selectFrom(jobInfo)
                .where(
                        titleLike(condition.getTitle()),
                        locationEq(condition.getLocation()),
                        salaryGoe(condition.getSalary()))
//                        personNumGoe(condition.getRecruitpersonnum()))
                .fetch()
                .size();

        log.info("쿼리 실행 후");
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleLike(String titleCond) {
        return hasText(titleCond) ? jobInfo.title.like("%" + titleCond + "%") : null;
    }

    private BooleanExpression locationEq(String locationCond) {
        return hasText(locationCond) ? jobInfo.location.eq(locationCond) : null;
    }

    private BooleanExpression salaryGoe(Long salaryCond) {
        return salaryCond != null ? jobInfo.salary.goe(salaryCond) : null;
    }

//    private BooleanExpression personNumGoe(String personNumCond) {
//        return hasText(personNumCond) ? jobInfo.recruitmentPersonNum.goe(Long.valueOf(personNumCond)) : null;
//    }
}
