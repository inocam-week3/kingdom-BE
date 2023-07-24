//package sparta.kingdombe.global.jwt;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import sparta.kingdombe.domain.user.dto.KakaoUserInfoDto;
//
//import java.util.Collection;
//
//public class KakaoAuthenticationImpl implements Authentication {
//
//    private final KakaoUserInfoDto kakaoUserInfo;
//
//    public KakaoAuthenticationImpl(KakaoUserInfoDto kakaoUserInfo) {
//        this.kakaoUserInfo = kakaoUserInfo;
//    }
//    public KakaoUserInfoDto getKakaoUserInfo() {
//        return this.kakaoUserInfo;
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return null;
//    }
//
//    @Override
//    public Object getDetails() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return null;
//    }
//
//    @Override
//    public boolean isAuthenticated() {
//        return false;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }
//}
