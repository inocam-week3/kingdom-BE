package sparta.kingdombe.global.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import sparta.kingdombe.global.stringCode.ErrorCodeEnum;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public ErrorResponse(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getMessage(), errorCodeEnum.getStatus());
    }

    public ErrorResponse(String message, HttpStatus status) {
        this(message, status.value());
    }
}
