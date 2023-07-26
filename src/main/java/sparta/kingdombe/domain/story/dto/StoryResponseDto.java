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
    private String image;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;
    private long viewCount;
    private boolean userLikes;

    public StoryResponseDto (Story story) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.content = story.getContent();
        this.liked = story.getLiked();
        this.username = story.getUser().getUsername();
        this.createdAt = story.getCreatedAt();
        this.viewCount = story.getViewCount();
        this.image = story.getImage();
        this.commentList = story.getCommentList().stream().map(CommentResponseDto::new).toList();
        this.userLikes = false;
    }

    public StoryResponseDto(Story story, boolean isLike) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.content = story.getContent();
        this.liked = story.getLiked();
        this.username = story.getUser().getUsername();
        this.createdAt = story.getCreatedAt();
        this.viewCount = story.getViewCount();
        this.image = story.getImage();
        this.commentList = story.getCommentList().stream().map(CommentResponseDto::new).toList();
        this.userLikes = isLike;
    }

    public StoryResponseDto All(Story story) {
        return new StoryResponseDto()
                .builder()
                .id(story.getId())
                .title(story.getTitle())
                .content(story.getContent())
                .liked(story.getLiked())
                .username(story.getUser().getUsername())
                .image(story.getImage())
                .createdAt(story.getCreatedAt())
                .viewCount(story.getViewCount())
                .build();
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

