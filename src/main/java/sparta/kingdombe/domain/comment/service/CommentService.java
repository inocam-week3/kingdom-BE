package sparta.kingdombe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.comment.dto.CommentRequestDto;
import sparta.kingdombe.domain.comment.dto.CommentResponseDto;
import sparta.kingdombe.domain.comment.entity.Comment;
import sparta.kingdombe.domain.comment.repository.CommentRepository;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.story.repository.StoryRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.exception.buisnessException.UnauthorizedException;
import sparta.kingdombe.global.exception.systemException.DataNotFoundException;

import static sparta.kingdombe.global.stringCode.SuccessCodeEnum.DELETE_SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final StoryRepository storyRepository;


    public CommentResponseDto createComment(Long storyId, CommentRequestDto commentRequestDto, User user) {
        Story story = findStory(storyId);
        Comment comment = new Comment(story, commentRequestDto, user);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = confirmComment(commentId, user);
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    public String deleteComment(Long commentId, User user) {
        Comment comment = confirmComment(commentId, user);
        commentRepository.delete(comment);
        return DELETE_SUCCESS.getMessage();
    }

    private Story findStory(Long storyId) {
        return storyRepository.findById(storyId).orElseThrow(() ->
                new DataNotFoundException("존재하지 않는 게시물입니다"));
    }

    private Comment confirmComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다"));
        if (!(comment.getUser().getId().equals(user.getId()))) {
            throw new UnauthorizedException("작성자만 수정,삭제가 가능합니다");
        }
        return comment;
    }
}
