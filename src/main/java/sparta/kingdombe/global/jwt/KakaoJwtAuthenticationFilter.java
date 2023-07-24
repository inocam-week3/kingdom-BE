//package sparta.kingdombe.global.jwt;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//import sparta.kingdombe.domain.user.dto.KakaoUserInfoDto;
//import sparta.kingdombe.domain.user.entity.User;
//import sparta.kingdombe.domain.user.entity.UserGenderEnum;
//import sparta.kingdombe.domain.user.entity.UserRoleEnum;
//import sparta.kingdombe.domain.user.repository.UserRepository;
//import sparta.kingdombe.global.responseDto.ApiResponse;
//import sparta.kingdombe.global.security.UserDetailsImpl;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.UUID;
//
//import static sparta.kingdombe.global.stringCode.ErrorCodeEnum.LOGIN_FAIL;
//import static sparta.kingdombe.global.stringCode.SuccessCodeEnum.USER_LOGIN_SUCCESS;
//import static sparta.kingdombe.global.utils.ResponseUtils.customError;
//import static sparta.kingdombe.global.utils.ResponseUtils.okWithMessage;
//
//@Slf4j(topic = "kakao")
//public class KakaoJwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private final JwtProvider jwtProvider;
//    private final RestTemplate restTemplate;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public KakaoJwtAuthenticationFilter(JwtProvider jwtProvider, RestTemplate restTemplate, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.jwtProvider = jwtProvider;
//        this.restTemplate = restTemplate;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        setFilterProcessesUrl("/api/auth/kakao");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        log.info("로그인 시도");
//        try {
//            String code = request.getParameter("code");
//            String accessToken = getToken(code);
//            KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
//            Authentication authentication = new KakaoAuthenticationImpl(kakaoUserInfo);
//
//            return getAuthenticationManager().authenticate(authentication);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        log.info("로그인 성공 및 JWT 생성");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getEmail();
//        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
//
//        String token = jwtProvider.createToken(email, role); // 토큰에 email과 권한(유저 or 기업)만 넣어둠
//        jwtProvider.addJwtHeader(token, response);
//
//        ApiResponse<?> apiResponse = okWithMessage(USER_LOGIN_SUCCESS);
//
//        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(jsonResponse);
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        log.info("로그인 실패");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        ApiResponse<?> apiResponse = customError(LOGIN_FAIL);
//
//        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(jsonResponse);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    }
//
//
//    private String getToken(String code) throws JsonProcessingException {
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://kauth.kakao.com")
//                .path("/oauth/token")
//                .encode()
//                .build()
//                .toUri();
//
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", "ca694ae46e22b997351afa5a92c6c63a");
//        body.add("redirect_uri", "http://localhost:8080/api/auth/kakao");
//        body.add("code", code);
//
//        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
//                .post(uri)
//                .headers(headers)
//                .body(body);
//
//        // HTTP 요청 보내기
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//        return jsonNode.get("access_token").asText();
//    }
//
//    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
//        // 요청 URL 만들기
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://kapi.kakao.com")
//                .path("/v2/user/me")
//                .encode()
//                .build()
//                .toUri();
//
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
//                .post(uri)
//                .headers(headers)
//                .body(new LinkedMultiValueMap<>());
//
//        // HTTP 요청 보내기
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class
//        );
//
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//        Long id = jsonNode.get("id").asLong();
//        String nickname = jsonNode.get("properties")
//                .get("nickname").asText();
//        String email = jsonNode.get("kakao_account")
//                .get("email").asText();
//        String gender = jsonNode.get("kakao_account")
//                .get("gender").asText();
//        UserGenderEnum genderEnum = gender.equals("male") ? UserGenderEnum.MALE : UserGenderEnum.FEMALE;
//
//        log.info("카카오: " + id + ", " + nickname + ", " + email);
//        return new KakaoUserInfoDto(id, nickname, email, genderEnum);
//    }
//
//    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        Long kakaoId = kakaoUserInfo.getId();
//        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);
//
//        if (kakaoUser == null) {
//            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
//            // 기존 회원가입을 kakaoEmail로 한경우
//            String kakaoEmail = kakaoUserInfo.getEmail();
//            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
//            if (sameEmailUser != null) {
//                kakaoUser = sameEmailUser;
//                // 기존 회원정보에 카카오 Id 추가
//                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
//            } else {
//                // 신규 회원가입
//                // password: random UUID
//                String password = UUID.randomUUID().toString(); // 랜덤, 사용자가 알 수 없게
//                String encodedPassword = passwordEncoder.encode(password);
//
//                kakaoUser = new User(kakaoUserInfo, encodedPassword, UserRoleEnum.USER);
//            }
//
//            userRepository.save(kakaoUser);
//        }
//        return kakaoUser;
//    }
//}
