package sparta.kingdombe.domain.home.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.home.entity.HomeStory;

import java.util.List;

public interface HomeStoryRepository extends JpaRepository<HomeStory, Long> {
    Page<HomeStory> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
