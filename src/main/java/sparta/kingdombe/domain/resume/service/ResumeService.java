package sparta.kingdombe.domain.resume.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.resume.dto.ResumeReadResponseDto;
import sparta.kingdombe.domain.resume.dto.ResumeRequestDto;
import sparta.kingdombe.domain.resume.dto.ResumeResponseDto;
import sparta.kingdombe.domain.resume.entity.Resume;
import sparta.kingdombe.domain.resume.repository.ResumeRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.exception.InvalidConditionException;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.stringCode.ErrorCodeEnum;
import sparta.kingdombe.global.stringCode.SuccessCodeEnum;
import sparta.kingdombe.global.utils.ResponseUtils;

import java.util.List;
import java.util.stream.Collectors;

import static sparta.kingdombe.global.utils.ResponseUtils.ok;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;

    // 전제 조회
    @Transactional(readOnly = true)
    public ApiResponse<?> findAllResume() {
        List<ResumeResponseDto> resumeList = resumeRepository.findAll()
                .stream()
                .map(ResumeResponseDto::new)
                .collect(Collectors.toList());
        return ok(resumeList);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public ApiResponse<?> getSelectedResume(Long resumeId) {
        Resume resume = resumeRepository
                .findDetailResume(resumeId)
                .orElseThrow(() ->
                        new InvalidConditionException(ErrorCodeEnum.POST_NOT_EXIST));
        return ok(new ResumeResponseDto(resume));
    }

    // 작성 페이지 조회
    public ApiResponse<?> getReadResume(User user) {
        return ok(new ResumeReadResponseDto(user));
    }

    // 인재 정보 생성
    public ApiResponse<?> createResume(ResumeRequestDto resumeRequestDto, User user) {
        Resume resume = new Resume(resumeRequestDto, user);
        resumeRepository.save(resume);
        return ok(new ResumeResponseDto(resume));
    }

    // 인재 정보 수정
    public ApiResponse<?> updateResume(Long resumeId, ResumeRequestDto resumeRequestDto, User user) {
        Resume resume = findResume(resumeId);
        checkUsername(resumeId, user);
        resume.update(resumeRequestDto);
        return ok(new ResumeResponseDto(resume));
    }

    // 인재 정보 삭제
    public ApiResponse<?> deleteResume(Long resumeId, User user) {
        Resume resume = findResume(resumeId);
        checkUsername(resumeId, user);
        resumeRepository.delete(resume);
        return ResponseUtils.okWithMessage(SuccessCodeEnum.POST_DELETE_SUCCESS);
    }

    private Resume findResume(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(() ->
                new InvalidConditionException(ErrorCodeEnum.POST_NOT_EXIST));
    }

    private void checkUsername(Long resumeId, User user) {
        Resume resume = findResume(resumeId);
        if (!(resume.getUser().getId().equals(user.getId()))) {
            throw new InvalidConditionException(ErrorCodeEnum.USER_NOT_MATCH);
        }
    }
}
