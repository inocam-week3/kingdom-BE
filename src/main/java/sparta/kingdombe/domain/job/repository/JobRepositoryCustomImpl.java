package sparta.kingdombe.domain.job.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.kingdombe.domain.job.dto.JobResponseDto;
import sparta.kingdombe.domain.job.dto.JobSearchCondition;
import sparta.kingdombe.domain.job.entity.QJobInfo;

import static sparta.kingdombe.domain.job.entity.QJobInfo.*;

@RequiredArgsConstructor
public class JobRepositoryCustomImpl implements JobRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public Page<JobResponseDto> searchJob(JobSearchCondition condition, Pageable pageable) {

        query
                .selectFrom(jobInfo)
                .
    }
}
