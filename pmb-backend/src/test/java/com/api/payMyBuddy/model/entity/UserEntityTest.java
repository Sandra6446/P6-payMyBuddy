package com.api.payMyBuddy.model.entity;

import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserEntityTest {

    private UserEntity userEntity;

    @BeforeEach
    private void setUpPerTest() {
        userEntity = new UserEntity(1, "user@email.com", "User", "Test", "pwd", 20, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void update() {
        userEntity.update(new User("", "", "", "", "", 0, new BankAccount("", "", "")));
        Assertions.assertEquals(userEntity, userEntity);

        userEntity.update(new User("newUser@email.com", "newPwd", "newPwd", "newUser", "newTest", 0, new BankAccount("newBank", "newIban", "newBic")));
        Assertions.assertEquals(new UserEntity(1, "newUser@email.com", "newUser", "newTest", "pwd", 20, "newBank", "newIban", "newBic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), userEntity);
    }

    @Test
    void updateBalance() {
        userEntity.updateBalance(100);
        Assertions.assertEquals(new UserEntity(1, "user@email.com", "User", "Test", "pwd", 120, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), userEntity);
    }
}