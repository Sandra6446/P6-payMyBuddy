package com.api.payMyBuddy.security.services;

import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Login;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {

    private static UserEntity userEntity;

    @MockBean
    private UserEntityRepository userEntityRepository;

    private UserDetailsServiceImpl userDetailsService;

    @BeforeAll
    private static void setUp() {
        userEntity = new UserEntity(1, "user@email.com", "User", "Test", "password encoded", 20, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @BeforeEach
    private void setUpPerTest() {
        userDetailsService = new UserDetailsServiceImpl(userEntityRepository);
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(userEntityRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(userEntity));
        Login login = new Login("user@email.com", "password encoded", null);
        Assertions.assertEquals(login, userDetailsService.loadUserByUsername(userEntity.getEmail()));

        Mockito.when(userEntityRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(userEntity.getEmail()));
    }
}
