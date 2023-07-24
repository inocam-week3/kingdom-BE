package sparta.kingdombe.domain.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.kingdombe.domain.story.entity.Story;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query("select s from Story s join fetch s.user")
    List<Story> findAll();
}
