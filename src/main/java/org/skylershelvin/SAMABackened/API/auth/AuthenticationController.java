package org.skylershelvin.SAMABackened.API.auth;

import jakarta.validation.Valid;
import org.skylershelvin.SAMABackened.API.model.RegistrationBody;
import org.skylershelvin.SAMABackened.exception.UserAlreadyExistException;
import org.skylershelvin.SAMABackened.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
   //register a user.
   private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @Valid
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody)  {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    };


}
