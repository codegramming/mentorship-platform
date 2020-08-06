package com.tayfurunal.mentorship.payload;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private Long id;

    private String email;

    private String username;

    private Collection<? extends GrantedAuthority> authorities;

    public LoginResponse(Long id, String email, String username, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.authorities = authorities;
    }
}

