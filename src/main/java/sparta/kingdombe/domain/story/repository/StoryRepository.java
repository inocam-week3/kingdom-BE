package sparta.kingdombe.domain.story.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sparta.kingdombe.domain.story.entity.Story;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long>, StoryRepositoryCustom {

    @Query("select s from Story s left join fetch s.user order by s.id DESC limit 10")
    List<Story> findStoryAtHome();

    @Query("select s from Story s left join fetch s.user")
    Page<Story> findAll(Pageable pageable);

    @Override
    @Query("select s from Story s left join fetch s.user where s.id = :id")
    Optional<Story> findById(@Param("id") Long storyId);
}
