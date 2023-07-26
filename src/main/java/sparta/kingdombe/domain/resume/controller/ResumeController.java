package sparta.kingdombe.domain.resume.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.resume.dto.ResumeRequestDto;
import sparta.kingdombe.domain.resume.service.ResumeService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;
import sparta.kingdombe.global.utils.ResponseUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    // 전체 조회
    @GetMapping
    public ApiResponse<?> getResumes(@RequestParam("page") int page,
                                     @RequestParam("size") int size) {
        return ResponseUtils.ok(resumeService.findAllResume(page,size));
    }

    // 상세 조회
    @GetMapping("/{resumeId}")
    public ApiResponse<?> getSelectedResume(@PathVariable Long resumeId) {
        return ResponseUtils.ok(resumeService.getSelectedResume(resumeId));
    }

    // 인재 정보 작성
    @PostMapping("/write")
    public ApiResponse<?> createResume(@RequestBody ResumeRequestDto resumeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(resumeService.createResume(resumeRequestDto, userDetails.getUser()));
    }

    // 인재 정보 수정
    @PatchMapping("/{resumeId}")
    public ApiResponse<?> updateResume(@PathVariable Long resumeId, @RequestBody ResumeRequestDto resumeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(resumeService.updateResume(resumeId, resumeRequestDto, userDetails.getUser()));
    }

    // 인재 정보 삭제
    @DeleteMapping("/{resumeId}")
    public ApiResponse<?> deleteResume(@PathVariable Long resumeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(resumeService.deleteResume(resumeId, userDetails.getUser()));
    }

    @GetMapping("/search")
    public ApiResponse<?> searchByCareer(String career) {
        return ResponseUtils.ok(resumeService.findResumeByCareer(career));
    }

}
