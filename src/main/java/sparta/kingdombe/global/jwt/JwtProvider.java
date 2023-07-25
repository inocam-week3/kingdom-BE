package sparta.kingdombe.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sparta.kingdombe.domain.user.entity.UserRoleEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    public static final String ACCESS_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "refreshtoken";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String NICKNAME_KEY = "nickname";
    private static final String BEARER_PREFIX = "Bearer ";
    public final long ACCESS_TOKEN_TIME = 30 * 60 * 1000L; // 30분
    public final long REFRESH_TOKEN_TIME = 3 * 24 * 60 * 60 * 1000L; // 3일

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // Header 토큰을 가져오기
    public String substringHeaderToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) { return token.substring(7);}
        throw new NullPointerException("유효한 토큰이 아닙니다");
    }

    // Header 토큰 담기
    public void addJwtHeader(String token, HttpServletResponse response, String header) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
            response.setHeader(header, token);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
        }
    }

    // Header 안에 있는 토큰 decode
    public String getTokenFromHeader(HttpServletRequest req, String header) {
        String token = req.getHeader(header);
        if(token != null) {
            try {
                return URLDecoder.decode(token, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.info(e.getMessage());
                return null;
            }
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String email, UserRoleEnum role, Long tokenTime, String name) {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim(AUTHORIZATION_KEY, role)
                        .claim(NICKNAME_KEY, name)
                        .setExpiration(new Date(date.getTime() + tokenTime))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        } return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String createAccessToken(String email, UserRoleEnum role, String name) {
        return this.createToken(email, role, ACCESS_TOKEN_TIME, name);
    }

    public String createRefreshToken(String email, UserRoleEnum role, String name) {
        return this.createToken(email, role, REFRESH_TOKEN_TIME, name);
    }

    public void addAccessJwtHeader(String accessToken, HttpServletResponse response) {
        addJwtHeader(accessToken, response, ACCESS_HEADER);
    }

    public void addRefreshJwtHeader(String accessToken, HttpServletResponse response) {
        addJwtHeader(accessToken, response, REFRESH_HEADER);
    }

    public String getAccessTokenFromHeader(HttpServletRequest req) {
        return this.getTokenFromHeader(req, ACCESS_HEADER);
    }

    public String getRefreshTokenFromHeader(HttpServletRequest req) {
        return this.getTokenFromHeader(req, REFRESH_HEADER);
    }

    public String createAccessTokenFromRefreshToken(Claims refreshInfo) {
        String email = refreshInfo.getSubject();
        UserRoleEnum role = UserRoleEnum.valueOf(refreshInfo.get(AUTHORIZATION_KEY, String.class));
        String name = refreshInfo.get(NICKNAME_KEY, String.class);

        return this.createAccessToken(email, role, name);
    }
}
