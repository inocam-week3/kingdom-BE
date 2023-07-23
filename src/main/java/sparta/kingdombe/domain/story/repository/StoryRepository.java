package sparta.kingdombe.domain.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.story.entity.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

}
