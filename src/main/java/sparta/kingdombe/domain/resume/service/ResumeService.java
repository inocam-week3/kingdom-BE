package sparta.kingdombe.domain.resume.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.resume.dto.ResumeRequestDto;
import sparta.kingdombe.domain.resume.dto.ResumeResponseDto;
import sparta.kingdombe.domain.resume.entity.Resume;
import sparta.kingdombe.domain.resume.repositroy.ResumeRepositroy;
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
    private final ResumeRepositroy resumeRepositroy;

    // 전제 조회
    @Transactional(readOnly = true)
    public ApiResponse<?> findAllResume() {
        List<ResumeResponseDto> resumeList = resumeRepositroy.findAll()
                .stream()
                .map(ResumeResponseDto::new)
                .collect(Collectors.toList());
        return ok(resumeList);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public ApiResponse<?> getSelectedResume(Long resumeid) {
        Resume resume = resumeRepositroy
                .findDetailResume(resumeid)
                .orElseThrow(() ->
                        new InvalidConditionException(ErrorCodeEnum.POST_NOT_EXIST));
        return ok(new ResumeResponseDto(resume));
    }

    // 인재정보 생성
    public ApiResponse<?> createResume(ResumeRequestDto resumeRequestDto, User user) {
        Resume resume = new Resume(resumeRequestDto, user);
        resumeRepositroy.save(resume);
        return ResponseUtils.okWithMessage(SuccessCodeEnum.POST_CREATE_SUCCESS);
    }

    // 인재정보 수정
    public ApiResponse<?> updateResume(Long resumeid, ResumeRequestDto resumeRequestDto, User user) {
        Resume resume = findResume(resumeid);
        checkUsername(resumeid, user);
        resume.update(resumeRequestDto);
        return ResponseUtils.okWithMessage(SuccessCodeEnum.POST_UPDATE_SUCCESS);
    }

    // 인재정보 삭제
    public ApiResponse<?> deleteResume(Long resumeid, User user) {
        Resume resume = findResume(resumeid);
        checkUsername(resumeid, user);
        resumeRepositroy.delete(resume);
        return ResponseUtils.okWithMessage(SuccessCodeEnum.POST_DELETE_SUCCESS);
    }

    private Resume findResume(Long resumeid) {
        return resumeRepositroy.findById(resumeid).orElseThrow(() ->
                new InvalidConditionException(ErrorCodeEnum.POST_NOT_EXIST));
    }

    private void checkUsername(Long resumeid, User user) {
        Resume resume = findResume(resumeid);
        if (!(resume.getUser().getId().equals(user.getId()))) {
            throw new InvalidConditionException(ErrorCodeEnum.USER_NOT_MATCH);
        }
    }
}
