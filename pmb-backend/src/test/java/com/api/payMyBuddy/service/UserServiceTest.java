package com.api.payMyBuddy.service;

import com.api.payMyBuddy.exceptions.AlreadyInDatabaseException;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private static String userEmail;
    private static UserEntity userEntity;
    private User user;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private UserService userService;

    @BeforeAll
    private static void setUp() {
        userEmail = "user@email.com";
        userEntity = new UserEntity(1, userEmail, "User", "Test", "password encoded", 20, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @BeforeEach
    private void setUpPerTest() {
        userService = new UserService(userDetailsService, userEntityRepository);
        userEntity = new UserEntity(1, userEmail, "User", "Test", "password encoded", 20, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        user = new User(userEntity);
    }

    @Test
    void createUser() {
        user.setConfirmPassword(user.getPassword());
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.empty());
        Mockito.when(userDetailsService.encode(ArgumentMatchers.anyString())).thenReturn("password encoded");
        Assertions.assertEquals(new ResponseEntity<>("User created", HttpStatus.CREATED), userService.createUser(user));

        // Test to add a registered user
        user.setConfirmPassword(user.getPassword());
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Assertions.assertThrows(AlreadyInDatabaseException.class, () -> userService.createUser(user));

        // Test to add an invalid user
        user.setConfirmPassword("");
        Assertions.assertEquals(new ResponseEntity<>("The two passwords are required", HttpStatus.BAD_REQUEST), userService.createUser(user));

        user.setConfirmPassword("another password");
        Assertions.assertEquals(new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST), userService.createUser(user));
    }

    @Test
    void getUser() {
        // Test to get an invalid user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> userService.getUser(userEmail));

        // Test to get a registered user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(ResponseEntity.ok(user), userService.getUser(userEmail));
    }

    @Test
    void updateUser() {

        User newProfile = new User(userEntity);
        newProfile.setPassword("newPassword");
        newProfile.setConfirmPassword(newProfile.getPassword());

        //Test to update a user without changing email
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userDetailsService.encode(ArgumentMatchers.anyString())).thenReturn("password encoded");
        Assertions.assertEquals(ResponseEntity.ok("Profile updated"), userService.updateUser(userEmail, newProfile));

        // Test to update a user with a new email
        newProfile.setEmail("newEmail");
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findByEmail("newEmail")).thenReturn(Optional.empty());
        Mockito.when(userDetailsService.encode(ArgumentMatchers.anyString())).thenReturn("password encoded");
        Assertions.assertEquals(ResponseEntity.ok("Profile updated"), userService.updateUser(userEmail, newProfile));

        // Test to update a user with an email already in database
        Mockito.when(userEntityRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Assertions.assertThrows(AlreadyInDatabaseException.class, () -> userService.updateUser(userEmail, newProfile));

        // Test to update an unregistered user
        Mockito.when(userEntityRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> userService.updateUser(userEmail, newProfile));

        // Test to update an invalid profile
        newProfile.setConfirmPassword("");
        Assertions.assertEquals(new ResponseEntity<>("Confirm password is required", HttpStatus.BAD_REQUEST), userService.updateUser(userEmail, newProfile));

        newProfile.setConfirmPassword("another password");
        Assertions.assertEquals(new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST), userService.updateUser(userEmail, newProfile));

    }
}