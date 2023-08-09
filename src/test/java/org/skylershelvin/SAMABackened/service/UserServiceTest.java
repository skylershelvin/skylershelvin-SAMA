package org.skylershelvin.SAMABackened.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.skylershelvin.SAMABackened.API.model.RegistrationBody;
import org.skylershelvin.SAMABackened.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    //testing if username is in existence
    @Test
    @Transactional
    public void testRegisterUser(){
        //build registration body
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("UserServiceTest$TestUserRegistration@Junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("MySecretPassword");
        //verify user will be rejected
        Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(body),
                "Username should already be in use.");
        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@Juint.com");
        //checking to see if email is rejected
        Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(body),
                "Username should already be in use.");
        body.setEmail("UserServiceTest$TestUserRegistration@Junit.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(body),
        "User should register successfully.");
    }

}
