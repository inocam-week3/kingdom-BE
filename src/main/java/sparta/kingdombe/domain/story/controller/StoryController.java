package sparta.kingdombe.domain.story.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.kingdombe.domain.story.dto.StoryRequestDto;
import sparta.kingdombe.domain.story.dto.StorySearchCondition;
import sparta.kingdombe.domain.story.service.StoryService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;
import sparta.kingdombe.global.utils.ResponseUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
@Slf4j
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    public ApiResponse<?> readAllStory() {
        return storyService.findAllStory();
    }

    @GetMapping("/{storyId}")
    public ApiResponse<?> readOneStory(@PathVariable Long storyId) {
        return storyService.findOnePost(storyId);
    }

    @PostMapping("/newstory")
    public ApiResponse<?> createStory(@RequestPart(value = "data") StoryRequestDto storyRequestDto,
                                      @RequestPart(value = "file", required = false) MultipartFile image,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

        return storyService.createStory(storyRequestDto, image, userDetailsImpl.getUser());
    }

    @PatchMapping("/{storyId}")
    public ApiResponse<?> modifyStory(@PathVariable Long storyId,
                                      @RequestPart(value = "data") StoryRequestDto storyRequestDto,
                                      @RequestPart(value = "file", required = false) MultipartFile file,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return storyService.updateStory(storyId, storyRequestDto, file, userDetailsImpl.getUser());
    }

    @DeleteMapping("/{storyId}")
    public ApiResponse<?> deleteStory(@PathVariable Long storyId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return storyService.deleteStory(storyId, userDetailsImpl.getUser());
    }

    @GetMapping("/search")
    public ApiResponse<?> searchStory(StorySearchCondition condition, Pageable pageable) {
        log.info("title = {}", condition.getTitle());
        return ResponseUtils.ok(storyService.searchStory(condition, pageable));
    }
}
