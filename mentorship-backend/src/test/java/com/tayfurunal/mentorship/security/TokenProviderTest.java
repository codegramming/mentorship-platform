package com.tayfurunal.mentorship.security;

import com.tayfurunal.mentorship.TestUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Mock
    private Authentication auth;

    @BeforeEach
    void init() {
        when(auth.getPrincipal()).thenReturn(TestUtils.createDummyUser());
    }

    @Test
    void validateTokenShouldReturnTrueWhenGetsValidToken() {
        //Prepare
        String token = tokenProvider.createToken(auth);

        //Test
        boolean isValid = tokenProvider.validateToken(token);

        //Verify
        assertThat(isValid).isTrue();
    }

    @Test
    void validateTokenShouldReturnFalseWhenGetsInvalidToken() {
        //Prepare
        String invalidToken = "wrong";

        //Test
        boolean isValid = tokenProvider.validateToken(invalidToken);

        //Verify
        assertThat(isValid).isFalse();
    }

    @Test
    void createTokenShouldReturnNotBlankToken() {
        //Test
        String token = tokenProvider.createToken(auth);

        //Verify
        assertThat(token).isNotBlank();
    }

    @Test
    void getUserIdFromTokenShouldReturnCorrectId() {
        //Prepare
        String token = tokenProvider.createToken(auth);

        //Test
        Long id = tokenProvider.getUserIdFromToken(token);

        //Verify
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(TestUtils.createDummyUser().getId());
    }
}
