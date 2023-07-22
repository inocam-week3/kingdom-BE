package sparta.kingdombe.global.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T>{

    private boolean success;
    private T info;
    private ErrorResponse error;

    public ApiResponse(boolean success, T info, ErrorResponse error) {
        this.success = success;
        this.info = info;
        this.error = error;
    }
}
