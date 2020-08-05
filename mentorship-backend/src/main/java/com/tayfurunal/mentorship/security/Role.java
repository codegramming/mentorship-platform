package com.tayfurunal.mentorship.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    /**
     * Default role that all users have
     */
    USER("ROLE_USER");

    private final String name;

    /**
     * Name of the role for spring security. Differs in that it must have the "ROLE_" prefix.
     * For example, for the USER role, the name must be the string "ROLE_USER"
     *
     * @param name role name
     */
    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
