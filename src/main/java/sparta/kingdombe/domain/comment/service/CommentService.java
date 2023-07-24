package sparta.kingdombe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.comment.dto.CommentRequestDto;
import sparta.kingdombe.domain.comment.dto.CommentResponseDto;
import sparta.kingdombe.domain.comment.entity.Comment;
import sparta.kingdombe.domain.comment.repository.CommentRepository;
import sparta.kingdombe.domain.story.dto.StoryResponseDto;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.story.repository.StoryRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.responseDto.ApiResponse;

import static sparta.kingdombe.global.utils.ResponseUtils.ok;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final StoryRepository storyRepository;


    public ApiResponse<?> createComment(Long storyId, CommentRequestDto commentRequestDto, User user) {
        Story story = findStory(storyId);
        Comment comment = new Comment(story, commentRequestDto, user);
        commentRepository.save(comment);
        return ok(new CommentResponseDto(comment));
    }

    public ApiResponse<?> updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = confirmComment(commentId, user);
        comment.update(commentRequestDto);
        return ok(new CommentResponseDto(comment));
    }

    public ApiResponse<?> deleteComment(Long commentId, User user) {
        Comment comment = confirmComment(commentId, user);
        commentRepository.delete(comment);
        return ok("삭제 완료");
    }

    private Story findStory(Long storyId) {
        return storyRepository.findById(storyId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));
    }

    private Comment confirmComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다"));
        if (!(comment.getUser().getId() == user.getId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정,삭제가 가능합니다");
        }
        return comment;
    }
}
