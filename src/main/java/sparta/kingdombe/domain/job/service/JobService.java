package sparta.kingdombe.domain.job.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.domain.job.dto.JobResponseDto;
import sparta.kingdombe.domain.job.entity.JobInfo;
import sparta.kingdombe.domain.job.repository.JobRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.responseDto.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

import static sparta.kingdombe.global.stringCode.SuccessCodeEnum.*;
import static sparta.kingdombe.global.utils.ResponseUtils.ok;
import static sparta.kingdombe.global.utils.ResponseUtils.okWithMessage;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JobService {
    private final JobRepository jobRepository;

    public ApiResponse<?> findAllJobInfo() {
        List<JobResponseDto> jobInfoList = jobRepository.findAll()
                .stream()
                .map(JobResponseDto::new)
                .collect(Collectors.toList());
        return ok(jobInfoList);
    }

    public ApiResponse<?> findJobInfoById(Long id) {
        return ok(new JobResponseDto(findJobInfo(id)));
    }

    public ApiResponse<?> createJob(JobRequestDto jobRequestDto, User user) {
        JobInfo jobInfo = new JobInfo(jobRequestDto, user);
        jobRepository.save(jobInfo);
        return okWithMessage(JOB_CREATE_SUCCESS);
    }


    public ApiResponse<?> update(Long id, JobRequestDto jobRequestDto, User user) {
        JobInfo jobinfo = findJobInfo(id);
        checkUsername(id, user);
        jobinfo.update(jobRequestDto);
        return okWithMessage(JOB_MODIFY_SUCCESS);
    }

    public ApiResponse<?> delete(Long id, User user) {
        JobInfo jobInfo = findJobInfo(id);
        checkUsername(id, user);
        jobRepository.delete(jobInfo);
        return okWithMessage(JOB_DELETE_SUCCESS);
    }

    private JobInfo findJobInfo(Long id) {
        return jobRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 채용 공고는 존재하지 않습니다.")
        );
    }

    private void checkUsername(Long id, User user) {
       JobInfo jobInfo = findJobInfo(id);
        if (!(jobInfo.getUser().getId().equals(user.getId())))
            throw new IllegalArgumentException("채용공고는 작성자만 수정,삭제가 가능합니다");
    }
}
