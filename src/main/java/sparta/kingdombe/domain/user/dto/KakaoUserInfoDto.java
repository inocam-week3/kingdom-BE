package sparta.kingdombe.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.user.entity.UserGenderEnum;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
    private UserGenderEnum gender;

    public KakaoUserInfoDto(Long id, String nickname, String email, UserGenderEnum gender) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }
}