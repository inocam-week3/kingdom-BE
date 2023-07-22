package sparta.kingdombe.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sparta.kingdombe.domain.user.dto.SignupRequestDto;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.domain.user.entity.UserGenderEnum;
import sparta.kingdombe.domain.user.entity.UserRoleEnum;
import sparta.kingdombe.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup(SignupRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserGenderEnum gender = requestDto.getGender().equals("Male") ? UserGenderEnum.MALE : UserGenderEnum.FEMALE;
        UserRoleEnum role = UserRoleEnum.USER;
        String enterpriseCode = requestDto.getEnterpriseCode();
        if(!enterpriseCode.equals("false")){
            // 검증
            role = UserRoleEnum.ENTERPRISE;
        }
        User user = new User(requestDto, password, gender, role);
        userRepository.save(user);
        return "가입 완료";
    }

    public String checkEmail(String email) {
        if (userRepository.existsByEmail(email)){
            return "중복된 이메일입니다.";
        }
        return "사용가능한 이메일입니다.";
    }
}
