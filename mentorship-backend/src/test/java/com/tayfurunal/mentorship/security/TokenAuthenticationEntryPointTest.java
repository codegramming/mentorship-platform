package com.tayfurunal.mentorship.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TokenAuthenticationEntryPointTest {

    @InjectMocks
    private TokenAuthenticationEntryPoint authenticationEntryPoint = new TokenAuthenticationEntryPoint();

    @Test
    public void testCommence() throws IOException {
        //Prepare
        final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        final AuthenticationException ex = Mockito.mock(AuthenticationException.class);

        //Test
        authenticationEntryPoint.commence(req, resp, ex);

        //Verify
        verify(resp, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
    }
}

