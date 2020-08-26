package com.tayfurunal.mentorship.security.oauth2;

import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.exception.OAuth2AuthenticationProcessingException;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.repository.jpa.UserRepository;
import com.tayfurunal.mentorship.security.AuthProvider;
import com.tayfurunal.mentorship.security.Role;
import com.tayfurunal.mentorship.security.oauth2.user.OAuth2UserInfo;
import com.tayfurunal.mentorship.security.oauth2.user.OAuth2UserInfoFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;


    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean checkEmail(String email) {
        List<String> emailList = new ArrayList<>();
        emailList.add("mentorshipobss@gmail.com");

        if (emailList.contains(email)) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("false");
            return false;
        }
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            log.error("Authentication cannot be processed", e);
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        final String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        if (StringUtils.isEmpty(userInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByUsername(userInfo.getUsername());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            AuthProvider provider = user.getProvider();

            if (!provider.equals(AuthProvider.valueOf(registrationId.toUpperCase()))) {
                StringBuilder message = new StringBuilder();
                final String providerName = String.valueOf(provider).toLowerCase();
                message.append("Looks like you're signed up with ").append(providerName)
                        .append(" account. Please use your ").append(providerName).append(" account to login.");
                throw new OAuth2AuthenticationProcessingException(message.toString());
            }
            user = updateExistingUser(user, userInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, userInfo);
        }
        user.setAuthorities(Collections.singleton(Role.USER));
        user.setAttributes(oAuth2User.getAttributes());
        return user;
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        final String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();


        if (!checkEmail(oAuth2UserInfo.getEmail())) {
            ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/auth/signup");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("email", "There is no registration.");
            apiError.setValidationErrors(validationErrors);
            throw new IllegalArgumentException("There is no email registration");
        }


        User user = new User();
        user.setProvider(AuthProvider.valueOf(registrationId.toUpperCase()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getUsername());
        user.setDisplayName(oAuth2UserInfo.getGivenName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo userInfo) {
        existingUser.setUsername(userInfo.getUsername());
        return userRepository.save(existingUser);
    }
}