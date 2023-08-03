package org.skylershelvin.SAMABackened.service;

import org.skylershelvin.SAMABackened.API.model.LoginBody;
import org.skylershelvin.SAMABackened.API.model.RegistrationBody;
import org.skylershelvin.SAMABackened.exception.UserAlreadyExistException;
import org.skylershelvin.SAMABackened.model.LocalUser;
import org.skylershelvin.SAMABackened.model.dao.LocalUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    //@Autowired
    private LocalUserDAO localUserDAO;
    //@Autowired
    private EncryptionService encryptionService;

    //@Autowired
    private JWTService jwtService;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException {
        //create a throw to catch already registered emails and usernames.
       if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() ||
                localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExistException();
        }


        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstname(registrationBody.getFirstName());
        user.setLastname(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user = localUserDAO.save(user);

        return user;
    }

    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent()) {
            // check is password is correct
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())){
                //is true we have valid user to return
                return jwtService.generationJWT(user);
          }
        }
        return null;
    }
}
