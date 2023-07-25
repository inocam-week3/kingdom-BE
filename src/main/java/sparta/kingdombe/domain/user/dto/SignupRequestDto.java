package sparta.kingdombe.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Pattern(regexp = "^[\\w]*@[\\w](.?[\\w])*.[a-zA-Z]{2,3}$", message = "이메일 형식에 맞춰주세요")
    @NotBlank
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[`~!@#$%^&*()])[a-zA-Z0-9`~!@#$%^&*()]{8,15}$", message = "영문자와 숫자, 특수문자를 포함한 8~15자 이내로 작성해주세요")
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    private String gender;
    private Long enterpriseCode;
}
