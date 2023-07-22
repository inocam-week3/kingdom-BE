package sparta.kingdombe.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import sparta.kingdombe.global.stringCode.ErrorCodeEnum;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static sparta.kingdombe.global.stringCode.ErrorCodeEnum.TOKEN_EXPIRED;
import static sparta.kingdombe.global.stringCode.ErrorCodeEnum.TOKEN_INVALID;
import static sparta.kingdombe.global.utils.ResponseUtils.customError;

public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException | NullPointerException | UnsupportedEncodingException e) {
            setErrorResponse(response, TOKEN_INVALID);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCodeEnum errorCodeEnum) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCodeEnum.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(objectMapper.writeValueAsString(customError(errorCodeEnum)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
