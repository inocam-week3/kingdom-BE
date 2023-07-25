package sparta.kingdombe.domain.story.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.kingdombe.domain.story.dto.StoryResponseDto;
import sparta.kingdombe.domain.story.dto.StorySearchCondition;

public interface StoryRepositoryCustom {

    Page<StoryResponseDto> searchStory(StorySearchCondition condition, Pageable pageable);
}
