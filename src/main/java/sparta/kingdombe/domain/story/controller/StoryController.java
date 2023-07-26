package sparta.kingdombe.domain.story.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.kingdombe.domain.story.dto.StoryRequestDto;
import sparta.kingdombe.domain.story.dto.StorySearchCondition;
import sparta.kingdombe.domain.story.service.StoryService;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;
import sparta.kingdombe.global.utils.ResponseUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    public ApiResponse<?> readAllStory(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseUtils.ok(storyService.findAllStory(page, size));
    }

    @GetMapping("/{storyId}")
    public ApiResponse<?> readOneStory(@PathVariable Long storyId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        Long userId = 0L;
        if (userDetailsImpl != null){
            userId = userDetailsImpl.getUser().getId();
        }
        return ResponseUtils.ok(storyService.findOnePost(storyId, userId));
    }

    @PostMapping("/newstory")
    public ApiResponse<?> createStory(@RequestPart(value = "data") StoryRequestDto storyRequestDto,
                                      @RequestPart(value = "file", required = false) MultipartFile image,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

        return ResponseUtils.ok(storyService.createStory(storyRequestDto, image, userDetailsImpl.getUser()));
    }

    @PatchMapping("/{storyId}")
    public ApiResponse<?> modifyStory(@PathVariable Long storyId,
                                      @RequestPart(value = "data") StoryRequestDto storyRequestDto,
                                      @RequestPart(value = "file", required = false) MultipartFile file,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseUtils.ok(storyService.updateStory(storyId, storyRequestDto, file, userDetailsImpl.getUser()));
    }

    @DeleteMapping("/{storyId}")
    public ApiResponse<?> deleteStory(@PathVariable Long storyId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseUtils.ok(storyService.deleteStory(storyId, userDetailsImpl.getUser()));
    }

    @GetMapping("/search")
    public ApiResponse<?> searchStory(StorySearchCondition condition, Pageable pageable) {
        return ResponseUtils.ok(storyService.searchStory(condition, pageable));
    }
}
