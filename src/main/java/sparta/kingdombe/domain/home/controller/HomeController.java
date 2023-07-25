package sparta.kingdombe.domain.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.kingdombe.domain.home.service.HomeService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.responseDto.ErrorResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/homejobs")
    public ApiResponse<?> getHome() throws IOException {
        return homeService.getHome();
    }

    @ExceptionHandler(IOException.class)
    public ApiResponse<?> handleIOException(IOException e) {
        ErrorResponse errorResponse = new ErrorResponse("조회할 수 없습니다.", 400);
        return new ApiResponse<>(false, null, errorResponse);
    }

    @GetMapping("/homestory")
    public ApiResponse<?> getStory() throws IOException {
        return homeService.getStory();
    }
}
