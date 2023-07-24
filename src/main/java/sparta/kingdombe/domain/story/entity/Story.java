package sparta.kingdombe.domain.story.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import sparta.kingdombe.domain.comment.entity.Comment;
import sparta.kingdombe.domain.story.dto.StoryRequestDto;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.utils.Timestamped;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor//(access = PROTECTED)
@Entity
@AllArgsConstructor
public class Story extends Timestamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "story_id")
    private Long id;

    private String title;
    private String content;
    private String image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private long liked;
    private long viewCount;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.initStory(this);
    }

    public Story(StoryRequestDto storyRequestDto, String image, User user) {
        this.title = storyRequestDto.getTitle();
        this.content = storyRequestDto.getContent();
        this.image = image;
        this.user = user;
    }

    public void update(StoryRequestDto storyRequestDto) {
        this.title = storyRequestDto.getTitle();
        this.content = storyRequestDto.getContent();
    }

    public void updateAll(StoryRequestDto storyRequestDto, String image) {
        this.title = storyRequestDto.getTitle();
        this.content = storyRequestDto.getContent();
        this.image = image;
    }

    public void increaseLike() {
        this.liked++;
    }

    public void decreaseLike() {
        this.liked--;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
