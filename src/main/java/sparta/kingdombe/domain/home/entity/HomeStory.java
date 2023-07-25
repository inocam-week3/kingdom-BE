package sparta.kingdombe.domain.home.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import sparta.kingdombe.global.utils.Timestamped;

@Entity
@Getter
public class HomeStory extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String content;

    private String storyDetailUrl;


}
