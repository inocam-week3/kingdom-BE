package sparta.kingdombe.domain.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.kingdombe.domain.job.entity.JobInfo;

import java.util.List;

public interface JobRepository extends JpaRepository<JobInfo, Long>, JobRepositoryCustom {

    List<JobInfo> findAll();

    @Query("select j from JobInfo j order by j.id DESC limit 40")
    List<JobInfo> findJobInfoAtHome();
}
