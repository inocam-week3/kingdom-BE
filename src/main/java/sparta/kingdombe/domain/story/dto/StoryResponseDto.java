package sparta.kingdombe.domain.story.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.story.entity.Story;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryResponseDto {

    private Long id;
    private String title;
    private String content;
    private long liked;
    private String username;
    private LocalDateTime createdAt;


    public StoryResponseDto(Story story) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.content = story.getContent();
        this.liked = story.getLiked();
        this.username = story.getUser().getUsername();
        this.createdAt = story.getCreatedAt();
    }
}

