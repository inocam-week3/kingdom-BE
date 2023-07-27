package sparta.kingdombe.domain.story.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sparta.kingdombe.domain.story.dto.QStoryResponseDto;
import sparta.kingdombe.domain.story.dto.StoryResponseDto;
import sparta.kingdombe.domain.story.dto.StorySearchCondition;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sparta.kingdombe.domain.story.entity.QStory.story;

@RequiredArgsConstructor
@Slf4j
public class StoryRepositoryCustomImpl implements StoryRepositoryCustom{

    private final JPAQueryFactory query;
    @Override
    public Page<StoryResponseDto> searchStory(StorySearchCondition condition, Pageable pageable) {
        log.info("쿼리 작성 시작");
        List<StoryResponseDto> content = query
                .select(new QStoryResponseDto(
                        story.id,
                        story.title,
                        story.content,
                        story.liked,
                        story.user.username,
                        story.viewCount,
                        story.createdAt
                ))
                .from(story)
                .where(
                        titleLike(condition.getTitle()),
                        contentLike(condition.getContent())
                )
                .orderBy(story.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query
                .selectFrom(story)
                .where(
                        titleLike(condition.getTitle()),
                        contentLike(condition.getContent()))
                .fetch()
                .size();

        log.info("쿼리 작성 종료");
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleLike(String titleCond) {
        log.info("titleCond = {}", titleCond);
        return hasText(titleCond) ? story.title.like("%" + titleCond + "%") : null;
    }

    private BooleanExpression contentLike(String contentCond) {
        return hasText(contentCond) ? story.content.like("%" + contentCond + "%") : null;
    }
}
