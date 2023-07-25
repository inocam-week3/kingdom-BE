package sparta.kingdombe.domain.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sparta.kingdombe.domain.resume.entity.Resume;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("select r from Resume r where r.id= :resumeid")
    Optional<Resume> findDetailResume(@Param("resumeid") Long resumeid);
}
