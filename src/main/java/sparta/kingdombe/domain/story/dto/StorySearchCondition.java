package sparta.kingdombe.domain.story.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StorySearchCondition {

    private String title;
    private String content;
}
