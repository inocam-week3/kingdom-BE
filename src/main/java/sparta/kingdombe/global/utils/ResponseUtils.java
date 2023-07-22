package sparta.kingdombe.global.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.responseDto.ErrorResponse;
import sparta.kingdombe.global.stringCode.ErrorCodeEnum;
import sparta.kingdombe.global.stringCode.SuccessCodeEnum;

@Getter
@NoArgsConstructor
public class ResponseUtils {

    public static <T> ApiResponse<T> ok(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<?> okWithMessage(SuccessCodeEnum successCodeEnum) {
        return new ApiResponse<>(true, successCodeEnum.getMessage(), null);
    }
    public static ApiResponse<?> error(String message, int status) {
        return new ApiResponse<>(false, null, new ErrorResponse(message, status));
    }

    public static ApiResponse<?> customError(ErrorCodeEnum errorCodeEnum) {
        return new ApiResponse<>(false, null, new ErrorResponse(errorCodeEnum));
    }
}
