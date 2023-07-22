package sparta.kingdombe.domain.user.entity;

public enum UserRoleEnum {
    USER(Authority.USER),
    ENTERPRISE(Authority.ENTERPRISE);

    private final String authority;

    UserRoleEnum(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }

    public static class Authority{
        public static final String USER = "ROLE_USER";
        public static final String ENTERPRISE = "ROLE_ENTERPRISE";
    }
}
