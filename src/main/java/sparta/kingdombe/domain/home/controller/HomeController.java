package sparta.kingdombe.domain.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.kingdombe.domain.home.service.HomeService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.utils.ResponseUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/homejobs")
    public ApiResponse<?> getHome() {
        return ResponseUtils.ok(homeService.getJobInfoAtHome());
    }

    @GetMapping("/homestories")
    public ApiResponse<?> getStory() {
        return ResponseUtils.ok(homeService.getStoryAtHome());
    }
}
