package sparta.kingdombe.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sparta.kingdombe.global.security.UserDetailsServiceImpl;


import java.io.IOException;

@Slf4j(topic = "JWT 검증, 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        log.info("검증중");
        String accessTokenValue = jwtProvider.getAccessTokenFromHeader(req);
        String refreshTokenValue = jwtProvider.getRefreshTokenFromHeader(req);

        if (!StringUtils.hasText(accessTokenValue) && StringUtils.hasText(refreshTokenValue)){ // access가 만료되서 안오고 refresh 토큰이 온경우
            log.info("확인중");
            refreshTokenValue = jwtProvider.substringHeaderToken(refreshTokenValue);

            if (!jwtProvider.validateToken(refreshTokenValue)){
                log.error("Token Error");
            }
            Claims refreshInfo = jwtProvider.getUserInfoFromToken(refreshTokenValue);
            accessTokenValue = jwtProvider.createAccessTokenFromRefreshToken(refreshInfo);
            jwtProvider.addAccessJwtHeader(accessTokenValue, res);

        }

        if (StringUtils.hasText(accessTokenValue)) { // 이 조건문이 없을경우 permitAll()한 부분들이 문제됨
            accessTokenValue = jwtProvider.substringHeaderToken(accessTokenValue);

            if (!jwtProvider.validateToken(accessTokenValue)) {
                log.error("Token Error");
            }

            Claims info = jwtProvider.getUserInfoFromToken(accessTokenValue);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUseremail(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

/*
무조건 access refresh 를 같이 받는 줄 알았으나
if (StringUtils.hasText(accessTokenValue)) {

            accessTokenValue = jwtProvider.substringHeaderToken(accessTokenValue);

            try {
                Claims info = jwtProvider.getUserInfoFromToken(accessTokenValue);
            }catch (ExpiredJwtException e){ // try에서 토큰이 만료된경우 throw e 를 통해 여기로 잡힌다.
                refreshTokenValue = jwtProvider.substringHeaderToken(refreshTokenValue);
                if (!jwtProvider.validateToken(refreshTokenValue)){
                    log.error("Token Error");
                }
                Claims info = jwtProvider.getUserInfoFromToken(accessTokenValue);
                jwtProvider.createAccessToken()
            }


            public Claims getUserInfoFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        } return null;
    }
 */

            /*
        String refreshTokenValue = jwtProvider.getRefreshTokenFromHeader(req);

        if (StringUtils.hasText(accessTokenValue)) {

            accessTokenValue = jwtProvider.substringHeaderToken(accessTokenValue);

            try {
                Claims info = jwtProvider.getUserInfoFromToken(accessTokenValue);
            }catch (ExpiredJwtException e){ // try에서 토큰이 만료된경우 throw e 를 통해 여기로 잡힌다.
                refreshTokenValue = jwtProvider.substringHeaderToken(refreshTokenValue);
                if (!jwtProvider.validateToken(refreshTokenValue)){
                    log.error("Token Error");
                }
                Claims info = jwtProvider.getUserInfoFromToken(accessTokenValue);

            }

            if (!jwtProvider.validateToken(accessTokenValue)) {
                log.error("Token Error");
            }

            Claims info = jwtProvider.getUserInfoFromToken(accessTokenValue);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
         */