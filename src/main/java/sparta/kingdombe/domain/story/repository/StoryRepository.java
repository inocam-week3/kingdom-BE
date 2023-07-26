package sparta.kingdombe.domain.story.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.kingdombe.domain.resume.entity.Resume;
import sparta.kingdombe.domain.story.entity.Story;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long>, StoryRepositoryCustom {

    @Query("select s from Story s order by s.id DESC limit 10")
    List<Story> findStoryAtHome();

    Page<Story> findAll(Pageable pageable);
}
