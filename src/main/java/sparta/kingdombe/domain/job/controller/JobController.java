package sparta.kingdombe.domain.job.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.domain.job.service.JobService;
import sparta.kingdombe.global.responseDto.ApiResponse;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("")
    public ApiResponse<?> createJob(@RequestBody JobRequestDto jobRequestDto){
       return jobService.createJob(jobRequestDto);
    }

    @GetMapping()

}
