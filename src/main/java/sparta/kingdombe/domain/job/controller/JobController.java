package sparta.kingdombe.domain.job.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.domain.job.service.JobService;
import sparta.kingdombe.global.responseDto.ApiResponse;


@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // 채용 공고 등록
    @PostMapping
    public ApiResponse<?> createJob(@RequestBody JobRequestDto jobRequestDto){
       return jobService.createJob(jobRequestDto);
    }

    // 채용 정보 조회
    @GetMapping
    public ApiResponse<?> getJob(){
        return jobService.findAllJobInfo();
    }

//    // 채용 정보 상세조회
//    @GetMapping("/{jobId}")
//
//    // 채용 정보 수정
//    @PatchMapping("/{jobId}")
//
//    // 채용 정보 삭제
//    @DeleteMapping("/{jobId}")
}
