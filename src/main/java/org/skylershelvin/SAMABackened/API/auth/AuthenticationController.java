package org.skylershelvin.SAMABackened.API.auth;

import jakarta.validation.Valid;
import org.skylershelvin.SAMABackened.API.model.LoginBody;
import org.skylershelvin.SAMABackened.API.model.LoginResponse;
import org.skylershelvin.SAMABackened.API.model.RegistrationBody;
import org.skylershelvin.SAMABackened.exception.UserAlreadyExistException;
import org.skylershelvin.SAMABackened.service.JWTService;
import org.skylershelvin.SAMABackened.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
   //register a user.
   // @Autowired
   private final UserService userService;


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
    }

   @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }
}
