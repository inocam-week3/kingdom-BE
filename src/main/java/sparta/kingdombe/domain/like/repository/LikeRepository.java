package sparta.kingdombe.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.like.entity.Like;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.user.entity.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByStoryAndUser(Story story, User user);

    Optional<Like> findByStoryIdAndUserId(Long storyId, Long id);
}
