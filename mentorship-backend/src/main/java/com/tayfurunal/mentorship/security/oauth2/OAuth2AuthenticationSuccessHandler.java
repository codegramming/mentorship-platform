package com.tayfurunal.mentorship.security.oauth2;

import com.tayfurunal.mentorship.config.CookieUtils;
import com.tayfurunal.mentorship.exception.BadRequestException;
import com.tayfurunal.mentorship.security.TokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import static com.tayfurunal.mentorship.security.oauth2.OAuth2AuthorizationRequestRepository.REDIRECT_URI_COOKIE;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final OAuth2AuthorizationRequestRepository requestRepository;

    @Value("#{'${app.oauth2.authorizedRedirectUris}'.split(',')}")
    private List<String> authorizeRedirectUris;

    @Autowired
    OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, OAuth2AuthorizationRequestRepository requestRepository) {
        this.tokenProvider = tokenProvider;
        this.requestRepository = requestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException {
        String targetUrl = determineTargetUrl(req, resp, auth);

        if (resp.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to {}", targetUrl);
            return;
        }

        clearAuthenticationAttributes(req, resp);
        getRedirectStrategy().sendRedirect(req, resp, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest req, HttpServletResponse resp, Authentication auth) {
        Optional<String> redirectUri = CookieUtils.getCookie(req, REDIRECT_URI_COOKIE).map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Unauthorized Redirect URI");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        String token = tokenProvider.createToken(auth);
        return UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest req, HttpServletResponse resp) {
        super.clearAuthenticationAttributes(req);
        requestRepository.removeAuthorizationRequestCookies(req, resp);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return authorizeRedirectUris.stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }
}