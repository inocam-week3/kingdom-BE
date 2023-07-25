package sparta.kingdombe.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sparta.kingdombe.domain.user.dto.SignupRequestDto;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.domain.user.entity.UserGenderEnum;
import sparta.kingdombe.domain.user.entity.UserRoleEnum;
import sparta.kingdombe.domain.user.repository.UserRepository;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.stringCode.ErrorCodeEnum;

import static sparta.kingdombe.global.stringCode.SuccessCodeEnum.*;
import static sparta.kingdombe.global.utils.ResponseUtils.customError;
import static sparta.kingdombe.global.utils.ResponseUtils.okWithMessage;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<?> signup(SignupRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserGenderEnum gender = requestDto.getGender().equals("male") ? UserGenderEnum.MALE : UserGenderEnum.FEMALE;
        UserRoleEnum role = UserRoleEnum.USER;
        Long enterpriseCode = requestDto.getEnterpriseCode();
        int [] enterpriseCodeArr = longToIntArray(enterpriseCode);
        System.out.println(enterpriseCodeArr.length);
        if(enterpriseCodeArr.length == 10){
            // 검증
            int sum = 0;
            for (int i =0; i<8; i++){
                switch (i%3) {
                    case 0 : sum += enterpriseCodeArr[i]*1; break;
                    case 1 : sum += enterpriseCodeArr[i]*3; break;
                    case 2 : sum += enterpriseCodeArr[i]*7; break;
                }
                System.out.println(enterpriseCodeArr[i]);
            }
            sum += enterpriseCodeArr[8]*11/2;
            System.out.println(sum);
            role = enterpriseCodeArr[9]== 10-(sum%10) ? UserRoleEnum.ENTERPRISE : UserRoleEnum.USER;
        }
        User user = new User(requestDto, password, gender, role);
        userRepository.save(user);
        return okWithMessage(USER_SIGNUP_SUCCESS);
    }

    public ApiResponse<?> checkEmail(String email) {
        if (userRepository.existsByEmail(email)){
            return customError(ErrorCodeEnum.DUPLICATE_EMAIL_EXIST);
        }
        return okWithMessage(CHECK_EMAIL_SUCCESS);
    }

    public static int[] longToIntArray(long number) {
        String numberStr = String.valueOf(number);
        int[] intArray = new int[numberStr.length()];
        for (int i = 0; i < numberStr.length(); i++) {
            intArray[i] = Character.getNumericValue(numberStr.charAt(i));
        }
        return intArray;
    }
}
