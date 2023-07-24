package sparta.kingdombe.domain.resume.dto;

import lombok.Getter;
import sparta.kingdombe.domain.user.entity.User;

@Getter
public class ResumeReadResponseDto {
    private String username;
    private String eamil;
    private String gender;

    public ResumeReadResponseDto(User user) {
        this.username = user.getUsername();
        this.eamil = user.getEmail();
        this.gender = user.getGender().toStringGender();
    }
}
