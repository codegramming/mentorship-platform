package com.tayfurunal.mentorship.security.oauth2.user;

import java.util.Map;

/**
 * An abstract class that describes template methods
 * for retrieving user information for a social provider
 */
public abstract class OAuth2UserInfo {
    /**
     * Authentication provider attributes
     */
    protected Map<String, Object> attributes;

    /**
     * @param attributes the attributes of the authentication provider
     */
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getUsername();

    public abstract String getGivenName();

    public abstract String getEmail();
}