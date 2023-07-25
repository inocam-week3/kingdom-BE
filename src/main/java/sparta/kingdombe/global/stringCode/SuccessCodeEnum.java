package sparta.kingdombe.global.stringCode;

import lombok.Getter;

@Getter
public enum SuccessCodeEnum {

    USER_SIGNUP_SUCCESS("회원가입 성공"),
    USER_LOGIN_SUCCESS("로그인 성공"),
    CHECK_EMAIL_SUCCESS("사용가능한 이메일입니다."),
    JOB_CREATE_SUCCESS("채용정보 추가 성공"),
    JOB_DELETE_SUCCESS("채용정보 삭제 성공"),
    JOB_MODIFY_SUCCESS("채용정보 수정 성공"),
    POST_CREATE_SUCCESS("게시글 작성 성공"),
    POST_UPDATE_SUCCESS("게시글 수정 성공"),
    POST_DELETE_SUCCESS("게시글 삭제 성공");


    private final String message;

    SuccessCodeEnum(String message) {
        this.message = message;
    }
}
