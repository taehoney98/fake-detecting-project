package com.aivle.fakedetecting.config.jwt;

import com.aivle.fakedetecting.enums.Role;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    @Getter
    private final Long id;
    private final String username;
    private final String token;
    @Getter
    private final Role role;

    public CustomAuthenticationToken(Long id, String username, String token, Role role) {
        super(new ArrayList<>());
        this.id = id;
        this.username = username;
        this.token = token;
        this.role = role;
        setAuthenticated(false); // 초기에는 인증되지 않은 상태
    }

    public CustomAuthenticationToken(Long id, String username, String token, Collection<? extends GrantedAuthority> authorities, Role role) {
        super(authorities);
        this.id = id;
        this.username = username;
        this.token = token;
        this.role = role;
        setAuthenticated(true); // 인증된 상태
    }

    @Override
    public Object getCredentials() {
        return this.token; // 토큰은 사용자 인증에 필요한 자격 증명
    }

    @Override
    public Object getPrincipal() {
        return this.username; // 사용자 이름은 인증된 사용자의 기본 정보
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(authenticated);
    }

}
