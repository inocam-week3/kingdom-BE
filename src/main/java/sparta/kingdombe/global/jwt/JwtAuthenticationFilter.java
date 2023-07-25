package sparta.kingdombe.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sparta.kingdombe.domain.user.dto.LoginRequestDto;
import sparta.kingdombe.domain.user.entity.UserRoleEnum;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.security.UserDetailsImpl;


import java.io.IOException;

import static sparta.kingdombe.global.stringCode.ErrorCodeEnum.LOGIN_FAIL;
import static sparta.kingdombe.global.stringCode.SuccessCodeEnum.USER_LOGIN_SUCCESS;
import static sparta.kingdombe.global.utils.ResponseUtils.customError;
import static sparta.kingdombe.global.utils.ResponseUtils.okWithMessage;


@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        ObjectMapper objectMapper = new ObjectMapper();
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getEmail();
        String name = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String accessToken = jwtProvider.createAccessToken(email, role, name); // access token 생성
        String refreshToken = jwtProvider.createRefreshToken(email, role, name); // refresh token 생성

        jwtProvider.addAccessJwtHeader(accessToken, response);
        jwtProvider.addRefreshJwtHeader(refreshToken, response);

        ApiResponse<?> apiResponse = okWithMessage(USER_LOGIN_SUCCESS);

        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        ObjectMapper objectMapper = new ObjectMapper();

        ApiResponse<?> apiResponse = customError(LOGIN_FAIL);

        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
