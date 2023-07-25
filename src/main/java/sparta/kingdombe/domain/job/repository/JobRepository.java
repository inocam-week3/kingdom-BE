package sparta.kingdombe.domain.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.job.entity.JobInfo;

import java.util.List;

public interface JobRepository extends JpaRepository<JobInfo, Long> {

    List<JobInfo> findAll();

    Page<JobInfo> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
