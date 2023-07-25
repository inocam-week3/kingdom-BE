package sparta.kingdombe.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.user.dto.KakaoUserInfoDto;
import sparta.kingdombe.domain.user.dto.SignupRequestDto;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String username;
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserGenderEnum gender;
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
    @Column
    private Long kakaoId;

    public User(SignupRequestDto requestDto, String password, UserGenderEnum gender, UserRoleEnum role) {
        this.email = requestDto.getEmail();
        this.password = password;
        this.username = requestDto.getUsername();
        this.gender = gender;
        this.role = role;
    }

    public User(KakaoUserInfoDto kakaoUserInfo, String password, UserRoleEnum role) {
        this.email = kakaoUserInfo.getEmail();
        this.password = password;
        this.username = kakaoUserInfo.getNickname();
        this.gender = kakaoUserInfo.getGender();
        this.role = role;
    }


    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
