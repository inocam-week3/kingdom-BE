package sparta.kingdombe.domain.resume.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.resume.dto.ResumeRequestDto;
import sparta.kingdombe.domain.resume.service.ResumeService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    // 전체 조회
    @GetMapping
    public ApiResponse<?> getResumes() {
        return resumeService.findAllResume();
    }

    // 상세 조회
    @GetMapping("/{resumeid}")
    public ApiResponse<?> getSelectedResume(@PathVariable Long resumeid) {
        return resumeService.getSelectedResume(resumeid);
    }

    // 생성
    @PostMapping
    public ApiResponse<?> createResume(@RequestBody ResumeRequestDto resumeRequestDto, @AuthenticationPrincipal UserDetailsImpl UserDetails) {
        return resumeService.createResume(resumeRequestDto, UserDetails.getUser());
    }

    // 수정
    @PatchMapping("/{resumeid}")
    public ApiResponse<?> updateResume(@PathVariable Long resumeid, @RequestBody ResumeRequestDto resumeRequestDto, @AuthenticationPrincipal UserDetailsImpl UserDetails) {
        return resumeService.updateResume(resumeid, resumeRequestDto, UserDetails.getUser());
    }

    // 삭제
    @DeleteMapping("/{resumeid}")
    public ApiResponse<?> deleteResume(@PathVariable Long resumeid, @AuthenticationPrincipal UserDetailsImpl UserDetails) {
        return resumeService.deleteResume(resumeid, UserDetails.getUser());
    }
}
