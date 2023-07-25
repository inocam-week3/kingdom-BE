package sparta.kingdombe.domain.story.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.comment.dto.CommentResponseDto;
import sparta.kingdombe.domain.story.entity.Story;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StoryResponseDto {

    private Long id;
    private String title;
    private String content;
    private long liked;
    private String username;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;
    private long viewCount;

    public StoryResponseDto (Story story) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.content = story.getContent();
        this.liked = story.getLiked();
        this.username = story.getUser().getUsername();
        this.createdAt = story.getCreatedAt();
        this.viewCount = story.getViewCount();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private long liked;
        private String username;
        private long viewCount;
        private LocalDateTime createdAt;
        private List<CommentResponseDto> commentList;
        private String image;

        public StoryResponseDto build() {
            return new StoryResponseDto(id, title, content, liked, username, createdAt, commentList, viewCount);
        }
    }

    @QueryProjection
    public StoryResponseDto(Long id, String title, String content, long liked, String username, long viewCount, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.liked = liked;
        this.username = username;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }
}

