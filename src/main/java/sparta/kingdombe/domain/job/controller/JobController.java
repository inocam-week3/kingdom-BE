package sparta.kingdombe.domain.job.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.domain.job.service.JobService;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
@Slf4j
public class JobController {

    private final JobService jobService;

    // 채용 공고 등록
    @PostMapping
    public ApiResponse<?> createJob(@RequestPart(value = "data") JobRequestDto jobRequestDto ,
                                    @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                    @RequestPart(value = "file2", required = false) MultipartFile multipartFile2,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return jobService.createJob(jobRequestDto, multipartFile, multipartFile2, userDetails.getUser());
    }

    // 채용 정보 조회
    @GetMapping("/search")
    public ApiResponse<?> getJob(@RequestParam("career") int page) {
        return jobService.findAllJobInfo(page);
    }

    // 채용 정보 상세조회
    @GetMapping("/{jobId}")
    public ApiResponse<?> getSelectJob(@PathVariable("jobId") Long id) {
        return jobService.findJobInfoById(id);
    }

    // 채용 정보 수정
    @PatchMapping("/{jobId}")
    public ApiResponse<?> updateJob(@PathVariable("jobId") Long id,
                                    @RequestPart(value = "data") JobRequestDto jobRequestDto ,
                                    @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                    @RequestPart(value = "file2", required = false) MultipartFile multipartFile2,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return jobService.update(id, jobRequestDto, multipartFile, multipartFile2, userDetails.getUser());
    }

    // 채용 정보 삭제
    @DeleteMapping("/{jobId}")
    public ApiResponse<?> deleteJob(@PathVariable("jobId") Long id,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return jobService.delete(id, userDetails.getUser());
    }
}
