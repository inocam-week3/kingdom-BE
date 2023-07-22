package sparta.kingdombe.domain.job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.domain.job.entity.JobInfo;
import sparta.kingdombe.domain.job.repository.JobRepository;
import sparta.kingdombe.global.responseDto.ApiResponse;

import java.util.List;

import static sparta.kingdombe.global.stringCode.SuccessCodeEnum.JOB_CREATE_SUCCESS;
import static sparta.kingdombe.global.utils.ResponseUtils.ok;
import static sparta.kingdombe.global.utils.ResponseUtils.okWithMessage;

@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    public ApiResponse<?> createJob(JobRequestDto jobRequestDto) {
        JobInfo jobInfo = new JobInfo(jobRequestDto);
        jobRepository.save(jobInfo);
        return okWithMessage(JOB_CREATE_SUCCESS);
    }

    public ApiResponse<?> findAllJobInfo() {
        List<JobInfo> jobInfoList = jobRepository.findAll();
        return ok(jobInfoList);
    }
}
