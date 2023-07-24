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

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private Long id;
        private String title;
        private String content;
        private long liked;
        private String username;
        private long viewCount;
        private LocalDateTime createdAt;
        private String image;

        public Create(Story story) {
            this.id = story.getId();
            this.title = story.getTitle();
            this.content = story.getContent();
            this.liked = story.getLiked();
            this.username = story.getUser().getUsername();
            this.viewCount = story.getViewCount();
            this.createdAt = story.getCreatedAt();
            this.image = story.getImage();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Read {
        private Long id;
        private String title;
        private String content;
        private long liked;
        private String username;
        private long viewCount;
        private LocalDateTime createdAt;
        private List<CommentResponseDto> commentList;
        private String image;

        public Read(Story story) {
            this.id = story.getId();
            this.title = story.getTitle();
            this.content = story.getContent();
            this.liked = story.getLiked();
            this.username = story.getUser().getUsername();
            this.viewCount = story.getViewCount();
            this.createdAt = story.getCreatedAt();
            this.image = story.getImage();
            this.commentList = story.getCommentList()
                    .stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList());
        }
    }



}

