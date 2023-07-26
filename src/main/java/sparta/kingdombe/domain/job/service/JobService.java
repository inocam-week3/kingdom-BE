package sparta.kingdombe.domain.job.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sparta.kingdombe.domain.job.dto.JobAllResponseDto;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.domain.job.dto.JobResponseDto;
import sparta.kingdombe.domain.job.dto.JobSearchCondition;
import sparta.kingdombe.domain.job.entity.JobInfo;
import sparta.kingdombe.domain.job.repository.JobRepository;
import sparta.kingdombe.domain.image.S3Service;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.exception.systemException.DataNotFoundException;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.stringCode.SuccessCodeEnum;

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
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public Page<JobAllResponseDto> findAllJobInfo(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobInfo> jobInfoPage = jobRepository.findAll(pageable);

        List<JobAllResponseDto> jobInfoList = jobInfoPage.stream()
                .map(JobAllResponseDto::new)
                .collect(Collectors.toList());

        long total = jobInfoPage.getTotalElements();

        // 해당 페이지에 들어갈 내용(리스트) , 요청한 페이지 정보 , 들어갈 내용들의 양
        return new PageImpl<>(jobInfoList, pageable, total);
    }

    public JobResponseDto findJobInfoById(Long id) {
        return new JobResponseDto(findJobInfo(id));
    }

    public JobResponseDto createJob(JobRequestDto jobRequestDto, MultipartFile multipartFile, MultipartFile multipartFile2, User user) {
        String image = s3Service.upload(multipartFile);
        String image2 = s3Service.upload(multipartFile2);
        JobInfo jobInfo = new JobInfo(jobRequestDto, image, image2, user);
        jobRepository.save(jobInfo);
        return new JobResponseDto(jobInfo);
    }


    public JobResponseDto update(Long id, JobRequestDto jobRequestDto, MultipartFile multipartFile, MultipartFile multipartFile2, User user) {
        JobInfo jobinfo = findJobInfo(id);
        checkUsername(id, user);
        updateStoryDetail(jobRequestDto, multipartFile, multipartFile2, jobinfo);
        return new JobResponseDto(jobinfo);
    }

    public String delete(Long id, User user) {
        JobInfo jobInfo = findJobInfo(id);
        checkUsername(id, user);
        deleteImage(jobInfo);
        jobRepository.delete(jobInfo);
        return DELETE_SUCCESS.getMessage();
    }

    private void deleteImage(JobInfo jobInfo) {
        String logoImageUrl = jobInfo.getLogoImage();
        String workInfraImageUrl = jobInfo.getWorkInfraImage();
        if (StringUtils.hasText(logoImageUrl)) {
            s3Service.delete(logoImageUrl);
        }
        if (StringUtils.hasText(workInfraImageUrl)) {
            s3Service.delete(workInfraImageUrl);
        }
    }

    private void updateStoryDetail(JobRequestDto jobRequestDto, MultipartFile multipartFile, MultipartFile multipartFile2, JobInfo jobInfo) {
        if (multipartFile != null && !multipartFile.isEmpty() || multipartFile2 != null && !multipartFile2.isEmpty()) {
            String logoImageUrl = jobInfo.getLogoImage();
            String workInfraImageUrl = jobInfo.getWorkInfraImage();
            String newlogoImageUrl = s3Service.upload(multipartFile);
            String newworkInfraImageUrl = s3Service.upload(multipartFile2);
            jobInfo.updateAll(jobRequestDto, newlogoImageUrl, newworkInfraImageUrl);

            if (StringUtils.hasText(logoImageUrl)) {
                s3Service.delete(logoImageUrl);
            }
            if (StringUtils.hasText(workInfraImageUrl)) {
                s3Service.delete(workInfraImageUrl);
            }
            jobInfo.update(jobRequestDto);
        }
    }

    private JobInfo findJobInfo(Long id) {
        return jobRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("존재하지 않는 게시물입니다")
        );
    }

    private void checkUsername(Long id, User user) {
       JobInfo jobInfo = findJobInfo(id);
        if (!(jobInfo.getUser().getId().equals(user.getId())))
            throw new DataNotFoundException("작성자만 수정,삭제가 가능합니다");
    }

    public Page<JobAllResponseDto> searchJobInfo(JobSearchCondition condition, Pageable pageable) {
        return jobRepository.searchJob(condition, pageable);
    }
}
