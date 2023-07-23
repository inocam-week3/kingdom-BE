package sparta.kingdombe.domain.story.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.kingdombe.domain.story.dto.StoryRequestDto;
import sparta.kingdombe.domain.story.service.StoryService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    public ApiResponse<?> readAllStory() {
        return storyService.findAllStory();
    }

    @GetMapping("/{storyid}")
    public ApiResponse<?> readOneStory(@PathVariable Long storyid) {
        return storyService.readOneStory(storyid);
    }

    @PostMapping("/newstory")
    public ApiResponse<?> createStory(@RequestPart(value = "data") StoryRequestDto storyRequestDto,
                                      @RequestPart(value = "file", required = false) MultipartFile image,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

        return storyService.createStory(storyRequestDto, image, userDetailsImpl.getUser());
    }

    @PatchMapping("/{storyid}")
    public ApiResponse<?> modifyStory(@PathVariable Long storyid,
                                      @RequestPart(value = "data") StoryRequestDto storyRequestDto,
                                      @RequestPart(value = "file", required = false) MultipartFile file,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return storyService.updateStory(storyid, storyRequestDto, file, userDetailsImpl.getUser());
    }

    @DeleteMapping("/{storyid}")
    public ApiResponse<?> deleteStory(@PathVariable Long storyid,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return storyService.deleteStory(storyid, userDetailsImpl.getUser());
    }
}
