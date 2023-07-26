package sparta.kingdombe.global.stringCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCodeEnum {

    TOKEN_INVALID(BAD_REQUEST, "유효한 토큰이 아닙니다."),
    TOKEN_EXPIRED(BAD_REQUEST, "토큰이 만료되었습니다"),
    LOGIN_FAIL(BAD_REQUEST, "로그인 실패"),
    DUPLICATE_USERNAME_EXIST(BAD_REQUEST, "중복된 사용자가 존재합니다"),
    DUPLICATE_EMAIL_EXIST(BAD_REQUEST, "중복된 이메일이 존재합니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCodeEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
