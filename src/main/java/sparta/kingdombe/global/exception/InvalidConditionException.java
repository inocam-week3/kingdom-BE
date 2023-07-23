package sparta.kingdombe.global.exception;

import sparta.kingdombe.global.stringCode.ErrorCodeEnum;

public class InvalidConditionException extends IllegalArgumentException{

    ErrorCodeEnum errorCodeEnum;

    public InvalidConditionException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }
}