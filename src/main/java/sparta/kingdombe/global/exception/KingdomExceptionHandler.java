package sparta.kingdombe.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.utils.ResponseUtils;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@ResponseBody
@Slf4j
public class KingdomExceptionHandler {


//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler(InvalidConditionException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiResponse<?> handleInvalidConditionException(InvalidConditionException e) {
        return ResponseUtils.customError(e.errorCodeEnum);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiResponse<?> handleRunttimeException(RuntimeException e) {
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
