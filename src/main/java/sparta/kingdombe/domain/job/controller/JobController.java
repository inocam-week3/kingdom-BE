package sparta.kingdombe.domain.job.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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
    public ApiResponse<?> createJob(@RequestBody JobRequestDto jobRequestDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return jobService.createJob(jobRequestDto, userDetails.getUser());
    }

    // 채용 정보 조회
    @GetMapping
    public ApiResponse<?> getJob() {
        return jobService.findAllJobInfo();
    }

    // 채용 정보 상세조회
    @GetMapping("/{jobid}")
    public ApiResponse<?> getSelectJob(@PathVariable("jobid") Long id) {
        return jobService.findJobInfoById(id);
    }

    // 채용 정보 수정
    @PatchMapping("/{jobid}")
    public ApiResponse<?> updateJob(@PathVariable("jobid") Long id,
                                    @RequestBody JobRequestDto jobRequestDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return jobService.update(id, jobRequestDto, userDetails.getUser());
    }

    // 채용 정보 삭제
    @DeleteMapping("/{jobid}")
    public ApiResponse<?> deleteJob(@PathVariable("jobid") Long id,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return jobService.delete(id, userDetails.getUser());
    }
}
