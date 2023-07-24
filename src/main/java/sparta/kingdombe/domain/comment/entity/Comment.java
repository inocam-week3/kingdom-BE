package sparta.kingdombe.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.comment.dto.CommentRequestDto;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.utils.Timestamped;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor//(access = PROTECTED)
@Getter
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "story_id")
    private Story story;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(Story story, CommentRequestDto commentRequestDto, User user) {
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.story = story;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public void initStory(Story story) {
        this.story = story;
    }
}
