package sparta.kingdombe.domain.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.kingdombe.domain.job.dto.JobAllResponseDto;
import sparta.kingdombe.domain.job.dto.JobSearchCondition;

public interface JobRepositoryCustom {

    Page<JobAllResponseDto> searchJob(JobSearchCondition condition, Pageable pageable);
}
