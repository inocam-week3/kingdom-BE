package sparta.kingdombe.domain.home.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.home.entity.Home;
import sparta.kingdombe.domain.home.entity.HomeStory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HomeStoryResponseDto {

    private Long id;
    private String username;
    private LocalDateTime createAt;
    private String content;
    private String storyDetailUrl;

    public HomeStoryResponseDto(HomeStory homeStory) {
        this.id = homeStory.getId();
        this.username = homeStory.getUsername();
        this.createAt = homeStory.getCreatedAt();
        this.content = homeStory.getContent();
        this.storyDetailUrl = homeStory.getStoryDetailUrl();
    }

}
