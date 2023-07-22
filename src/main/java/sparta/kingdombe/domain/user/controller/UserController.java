package sparta.kingdombe.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.user.dto.SignupRequestDto;
import sparta.kingdombe.domain.user.service.UserService;
import sparta.kingdombe.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @PostMapping("/auth/signup")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @GetMapping("/auth/email")
    public String checkEmail(@RequestParam String email){
        return userService.checkEmail(email);
    }

    @GetMapping("/auth")
    public String chechToken(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails.getUser().getEmail());
        return userDetails.getUser().getRole().toString();
    }

//    @PostMapping("/job")
//    public String test(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        System.out.println(userDetails.getUser().getEmail());
//        return userDetails.getUser().getRole().toString();
//    }

    @GetMapping("/homejobs")
    public String test2(){
        return "정상";
    }
}
