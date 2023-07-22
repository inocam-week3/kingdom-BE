package sparta.kingdombe.global.stringCode;

import lombok.Getter;

@Getter
public enum SuccessCodeEnum {

    USER_SIGNUP_SUCCESS("회원가입 성공"),
    USER_LOGIN_SUCCESS("로그인 성공"),
    JOB_CREATE_SUCCESS("채용정보 추가 성공")
    ;

    private final String message;

    SuccessCodeEnum(String message) {
        this.message = message;
    }
}
