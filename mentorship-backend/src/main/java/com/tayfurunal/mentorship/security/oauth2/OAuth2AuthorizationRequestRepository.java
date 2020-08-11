package com.tayfurunal.mentorship.security.oauth2;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.tayfurunal.mentorship.config.CookieUtils;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE = "oauth2_auth_request";
    public static final String REDIRECT_URI_COOKIE = "redirect_uri";
    private static final int cookieExpireSeconds = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest req) {
        return CookieUtils.getCookie(req, OAUTH2_AUTHORIZATION_REQUEST_COOKIE)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest req, HttpServletResponse resp) {
        if (authorizationRequest != null) {
            CookieUtils.addCookie(resp, OAUTH2_AUTHORIZATION_REQUEST_COOKIE,
                    CookieUtils.serialize(authorizationRequest),
                    cookieExpireSeconds);

            String redirectUriAfterLogin = req.getParameter(REDIRECT_URI_COOKIE);
            if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
                CookieUtils.addCookie(resp, REDIRECT_URI_COOKIE, redirectUriAfterLogin, cookieExpireSeconds);
            }
        } else {
            CookieUtils.deleteCookie(req, resp, OAUTH2_AUTHORIZATION_REQUEST_COOKIE);
            CookieUtils.deleteCookie(req, resp, REDIRECT_URI_COOKIE);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_COOKIE);
    }
}