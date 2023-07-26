package sparta.kingdombe.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sparta.kingdombe.domain.user.dto.KakaoUserInfoDto;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.domain.user.entity.UserGenderEnum;
import sparta.kingdombe.domain.user.entity.UserRoleEnum;
import sparta.kingdombe.domain.user.repository.UserRepository;
import sparta.kingdombe.global.exception.buisnessException.ConditionDisagreeException;
import sparta.kingdombe.global.jwt.JwtProvider;
import sparta.kingdombe.global.responseDto.ApiResponse;

import java.net.URI;
import java.util.UUID;

import static sparta.kingdombe.global.stringCode.SuccessCodeEnum.USER_LOGIN_SUCCESS;
import static sparta.kingdombe.global.utils.ResponseUtils.okWithMessage;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;

    public ApiResponse<?> kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String kakaoAccessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. JWT 토큰 반환
        String accessToken = jwtProvider.createAccessToken(kakaoUser.getEmail(), kakaoUser.getRole(), kakaoUser.getUsername(), kakaoUser.getGender());
        String refreshToken = jwtProvider.createRefreshToken(kakaoUser.getEmail(), kakaoUser.getRole(), kakaoUser.getUsername(), kakaoUser.getGender());
        jwtProvider.addAccessJwtHeader(accessToken, response);
        jwtProvider.addRefreshJwtHeader(refreshToken, response);

        return okWithMessage(USER_LOGIN_SUCCESS);
    }

    private String getToken(String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "ca694ae46e22b997351afa5a92c6c63a");
        body.add("redirect_uri", "http://3.34.136.177/api/auth/kakao");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        try {
            Long id = jsonNode.get("id").asLong();
            String nickname = jsonNode.get("properties")
                    .get("nickname").asText();
            String email = jsonNode.get("kakao_account")
                    .get("email").asText();
            String gender = jsonNode.get("kakao_account")
                    .get("gender").asText();

            UserGenderEnum genderEnum = gender.equals("male") ? UserGenderEnum.MALE : UserGenderEnum.FEMALE;

            log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
            return new KakaoUserInfoDto(id, nickname, email, genderEnum);
        }catch (Exception e){
            throw new ConditionDisagreeException("권한을 허용해 주세요", e);
        }
    }

    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인 // 이미 가입했는지 - 처음인지
        Long kakaoId = kakaoUserInfo.getId(); // @kakao.com // naver.com
        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            // 기존 회원가입을 kakaoEmail로 한경우
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser; // kakaoId || default
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString(); // 랜덤, 사용자가 알 수 없게
                String encodedPassword = passwordEncoder.encode(password);

                kakaoUser = new User(kakaoUserInfo, encodedPassword, UserRoleEnum.USER);
            }

            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

}
