package sparta.kingdombe.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.user.dto.SignupRequestDto;
import sparta.kingdombe.domain.user.service.KakaoService;
import sparta.kingdombe.domain.user.service.UserService;
import sparta.kingdombe.global.responseDto.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;
    @PostMapping("/auth/signup")
    public ApiResponse<?> signup(@RequestBody @Valid SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @GetMapping("/auth/email")
    public ApiResponse<?> checkEmail(@RequestParam String email){
        return userService.checkEmail(email);
    }
    @GetMapping("/auth/kakao") // GET https://kauth.kakao.com/oauth/authorize?client_id=ca694ae46e22b997351afa5a92c6c63a&response_type=code&redirect_uri=http://localhost:8080/api/auth/kakao
    public ApiResponse<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }
}
