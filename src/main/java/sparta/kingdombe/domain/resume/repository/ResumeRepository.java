package sparta.kingdombe.domain.resume.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sparta.kingdombe.domain.resume.entity.Resume;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("select r from Resume r where r.career = :career")
    List<Resume> findByCareer(@Param("career") String career);

    Page<Resume> findAll(Pageable pageable);
}
