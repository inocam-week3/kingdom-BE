package sparta.kingdombe.domain.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.kingdombe.domain.job.entity.JobInfo;

public interface JobRepository extends JpaRepository<JobInfo, Long> {
}
