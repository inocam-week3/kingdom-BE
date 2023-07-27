package sparta.kingdombe.domain.home.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.story.entity.Story;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HomeStoryResponseDto {

    private Long id;
    private String username;
    private LocalDateTime createAt;
    private String content;
    private String storyDetailUrl;
    private String image;

    public HomeStoryResponseDto(Story story) {
        this.id = story.getId();
        this.username = story.getUser().getUsername();
        this.createAt = story.getCreatedAt();
        this.content = story.getContent();
        this.storyDetailUrl = "http://3.34.136.177/api/stories/" + story.getId();
        this.image = story.getImage();
    }

}
