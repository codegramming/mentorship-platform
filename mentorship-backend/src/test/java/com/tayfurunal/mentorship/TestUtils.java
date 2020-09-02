package com.tayfurunal.mentorship;

import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.payload.LoginRequest;
import com.tayfurunal.mentorship.payload.SignUpRequest;

public class TestUtils {

    public static User createDummyUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setUsername("test-username");
        user.setPassword("test-pw");
        return user;
    }

    public static User createDummyUserForPersist() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setDisplayName("Test");
        user.setUsername("test-username");
        user.setPassword("test-pw");
        return user;
    }

    public static SignUpRequest createDummySignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setUsername("test-username");
        signUpRequest.setPassword("test-pw");
        return signUpRequest;
    }

    public static LoginRequest createDummyLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test");
        loginRequest.setPassword("test-pw");
        return loginRequest;
    }
}
