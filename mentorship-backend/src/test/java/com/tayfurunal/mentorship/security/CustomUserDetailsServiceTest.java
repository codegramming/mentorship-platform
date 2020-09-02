package com.tayfurunal.mentorship.security;

import com.tayfurunal.mentorship.TestUtils;
import com.tayfurunal.mentorship.exception.ResourceNotFoundException;
import com.tayfurunal.mentorship.repository.jpa.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userService;

    @Test
    void loadUserByUsernameShouldReturnCorrectUserDetails() {
        //Prepare
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(TestUtils.createDummyUser()));

        //Test
        UserDetails userDetails = userService.loadUserByUsername(anyString());

        //Verify
        assertThat(userDetails.getUsername()).isEqualTo("test-username");
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void whenLoadUserByUsernameGetsNonexistentEmailThanThrowException() {
        //Prepare
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //Verify
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(anyString()));
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void loadUserByIdShouldReturnCorrectUserDetails() {
        //Prepare
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(TestUtils.createDummyUser()));

        //Test
        UserDetails userDetails = userService.loadUserById(1L);

        //Verify
        assertThat(userDetails.getUsername()).isEqualTo("test-username");
        verify(userRepository).findById(anyLong());
    }

    @Test
    void whenLoadUserByIdGetsNonexistentIdThanThrowException() {
        //Prepare
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Verify
        assertThrows(ResourceNotFoundException.class, () -> userService.loadUserById(anyLong()));
        verify(userRepository).findById(anyLong());
    }
}
