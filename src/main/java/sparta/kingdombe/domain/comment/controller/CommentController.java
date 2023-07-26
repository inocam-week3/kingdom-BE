package sparta.kingdombe.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.comment.dto.CommentRequestDto;
import sparta.kingdombe.domain.comment.service.CommentService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;
import sparta.kingdombe.global.utils.ResponseUtils;

@RestController
@RequestMapping("/api/stories/{storyId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<?> createComment(@PathVariable Long storyId,
                                        @RequestBody CommentRequestDto commentRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(commentService.createComment(storyId, commentRequestDto, userDetails.getUser()));
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<?> updateComment(@PathVariable Long commentId,
                                        @RequestBody CommentRequestDto commentRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(commentService.updateComment(commentId, commentRequestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<?> deleteComment(@PathVariable Long commentId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(commentService.deleteComment(commentId, userDetails.getUser()));
    }
}
