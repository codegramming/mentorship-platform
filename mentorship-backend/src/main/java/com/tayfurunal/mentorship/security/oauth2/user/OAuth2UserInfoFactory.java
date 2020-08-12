package com.tayfurunal.mentorship.security.oauth2.user;

import com.tayfurunal.mentorship.exception.OAuth2AuthenticationProcessingException;
import com.tayfurunal.mentorship.security.AuthProvider;

import java.util.Map;

/**
 * This class is the OAuth2UserInfo instance factory
 */
public class OAuth2UserInfoFactory {

    /**
     * Returns the corresponding registrationId instance of the OAuth2UserInfo class.
     * For example, if registrationId is "google", a new object of the GoogleOAuth2UserInfo class is returned
     *
     * @param registrationId a string containing the name of the authentication provider
     * @param attributes     authentication provider attributes
     * @return implementation of the OAuth2UserInfo class
     */
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            System.out.println(attributes);
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
        }
    }
}