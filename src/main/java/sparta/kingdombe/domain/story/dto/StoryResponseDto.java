package sparta.kingdombe.domain.story.dto;

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

    public StoryResponseDto Detail(Story story) {
        StoryResponseDto storyResponseDto = new StoryResponseDto();

        return storyResponseDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .content(story.getContent())
                .liked(story.getLiked())
                .username(story.getUser().getUsername())
                .createdAt(story.getCreatedAt())
                .viewCount(story.getViewCount())
                .commentList(story.getCommentList()
                        .stream()
                        .map(CommentResponseDto::new)
                        .collect(Collectors.toList()))
                .build();
    }
}

