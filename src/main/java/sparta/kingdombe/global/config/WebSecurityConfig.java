package sparta.kingdombe.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sparta.kingdombe.global.jwt.JwtAuthenticationFilter;
import sparta.kingdombe.global.jwt.JwtAuthorizationFilter;
import sparta.kingdombe.global.jwt.JwtExceptionFilter;
import sparta.kingdombe.global.jwt.JwtProvider;
import sparta.kingdombe.global.security.UserDetailsServiceImpl;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, userDetailsService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 배포주소 넣기 "http://inocam-week6.s3-website.ap-northeast-2.amazonaws.com",
        config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT","OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.addExposedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 설정, CORS 설정, 기존 세션 방식 -> JWT 방식
        http
                .csrf((csrf) -> csrf.disable())
                .cors(withDefaults())
                .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/api/auth/**","/api/homejobs","/api/homestories").permitAll()
                                .requestMatchers(GET, "/api/job/**","/api/resumes/**","/api/stories/**").permitAll()
                                .requestMatchers(GET, "/api/resumes/**").permitAll()
                                .requestMatchers(GET, "/api/stories/**").permitAll()
                                .requestMatchers(POST, "/api/job/**").hasRole("ENTERPRISE")
                                .requestMatchers(PATCH, "/api/job/**").hasRole("ENTERPRISE")
                                .requestMatchers(DELETE, "/api/job/**").hasRole("ENTERPRISE")
                                .requestMatchers(POST, "/api/resumes/**").hasRole("USER")
                                .requestMatchers(PATCH, "/api/resumes/**").hasRole("USER")
                                .requestMatchers(DELETE, "/api/resumes/**").hasRole("USER")
                                .anyRequest().authenticated()) // 그 외 모든 요청 인증처리
                .addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }
}

