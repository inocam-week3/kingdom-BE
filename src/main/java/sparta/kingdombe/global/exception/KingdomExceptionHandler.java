package sparta.kingdombe.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sparta.kingdombe.global.exception.buisnessException.ConditionDisagreeException;
import sparta.kingdombe.global.exception.buisnessException.UnauthorizedException;
import sparta.kingdombe.global.exception.buisnessException.UploadException;
import sparta.kingdombe.global.exception.systemException.DataNotFoundException;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.utils.ResponseUtils;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@ResponseBody
@Slf4j
public class KingdomExceptionHandler {

    @ExceptionHandler(UploadException.class)
    @ResponseStatus(BAD_GATEWAY)
    public ApiResponse<?> handleUploadException(UploadException e) {
        log.error("error message = {}", e.getMessage());
        return ResponseUtils.error(e.getMessage(), BAD_REQUEST.value());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ApiResponse<?> handleUnauthorizedException(UnauthorizedException e) {
        log.error("error message = {}", e.getMessage());
        return ResponseUtils.error(e.getMessage(), UNAUTHORIZED.value());
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiResponse<?> handleDataNotFoundException(DataNotFoundException e) {
        log.error("error message = {}", e.getMessage());
        return ResponseUtils.error(e.getMessage(), NOT_FOUND.value());
    }

    @ExceptionHandler(ConditionDisagreeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiResponse<?> handleConditionDisagreeException(ConditionDisagreeException e) {
        log.error("error message = {}", e.getMessage());
        return ResponseUtils.error(e.getMessage(), BAD_REQUEST.value());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("error message = {}", e.getMessage());
        return ResponseUtils.error(e.getMessage(), BAD_REQUEST.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiResponse<?> handlerException(Exception e) {
        log.error("error message = {}", e.getMessage());
        return ResponseUtils.error(e.getMessage(), BAD_REQUEST.value());
    }

}
