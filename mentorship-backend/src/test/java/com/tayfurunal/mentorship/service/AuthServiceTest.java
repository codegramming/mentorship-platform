package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.TestUtils;
import com.tayfurunal.mentorship.payload.LoginRequest;
import com.tayfurunal.mentorship.payload.SignUpRequest;
import com.tayfurunal.mentorship.repository.jpa.UserRepository;
import com.tayfurunal.mentorship.security.TokenProvider;
import com.tayfurunal.mentorship.service.impl.AuthServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private final AuthService sut = new AuthServiceImpl(userRepository, passwordEncoder, authenticationManager,
            tokenProvider);

    @Test
    public void testRegisterUserWhenUsernameExistsReturnBadRequest() {
        //Prepare
        final SignUpRequest signUpRequest = TestUtils.createDummySignUpRequest();
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);

        //Test
        final ResponseEntity<?> responseEntity = sut.registerUser(signUpRequest);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testRegisterUserWhenMailExistsReturnBadRequest() {
        //Prepare
        final SignUpRequest signUpRequest = TestUtils.createDummySignUpRequest();
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        //Test
        final ResponseEntity<?> responseEntity = sut.registerUser(signUpRequest);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testRegisterUserWhenSuccessReturnCreated() {
        //Prepare
        final SignUpRequest signUpRequest = TestUtils.createDummySignUpRequest();
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);

        //Test
        final ResponseEntity<?> responseEntity = sut.registerUser(signUpRequest);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testAuthenticateUserWhenSuccessReturnOk() {
        //Prepare
        final LoginRequest loginRequest = TestUtils.createDummyLoginRequest();

        //Test
        final ResponseEntity<?> responseEntity = sut.authenticateUser(loginRequest);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}

