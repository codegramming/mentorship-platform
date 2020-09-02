package com.tayfurunal.mentorship.security;

import com.tayfurunal.mentorship.TestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenAuthenticationFilterTest {

    @InjectMocks
    private final TokenAuthenticationFilter authenticationFilter = new TokenAuthenticationFilter();

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testDoFilterInternal() throws ServletException, IOException {
        //Prepare
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer DummyToken");
        when(tokenProvider.validateToken("DummyToken")).thenReturn(true);
        when(tokenProvider.getUserIdFromToken("DummyToken")).thenReturn(0L);
        when(customUserDetailsService.loadUserById(0L)).thenReturn(TestUtils.createDummyUser());

        //Test
        authenticationFilter.doFilterInternal(request, response, chain);

        //Verify
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_NoToken() throws ServletException, IOException {
        //Prepare
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);
        when(request.getHeader("Authorization")).thenReturn("");

        //Test
        authenticationFilter.doFilterInternal(request, response, chain);

        //Verify
        verify(chain, times(1)).doFilter(request, response);
    }

}