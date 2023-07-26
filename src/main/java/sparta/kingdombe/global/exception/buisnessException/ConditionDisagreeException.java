package sparta.kingdombe.global.exception.buisnessException;

public class ConditionDisagreeException extends RuntimeException {
    public ConditionDisagreeException() {
        super();
    }

    public ConditionDisagreeException(String message) {
        super(message);
    }

    public ConditionDisagreeException(String message, Throwable cause) {
        super(message, cause);
    }
}
