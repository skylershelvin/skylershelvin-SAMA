package org.skylershelvin.SAMABackened.service;

import org.skylershelvin.SAMABackened.API.model.RegistrationBody;
import org.skylershelvin.SAMABackened.exception.UserAlreadyExistException;
import org.skylershelvin.SAMABackened.model.LocalUser;
import org.skylershelvin.SAMABackened.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;


@Service
public class UserService {

    private LocalUserDAO localUserDAO;

    public UserService(LocalUserDAO localUserDAO){
        this.localUserDAO = localUserDAO;
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
        //TO DO : Encrypt passwords
        user.setPassword(registrationBody.getPassword());
        user = localUserDAO.save(user);

        return user;
    }
}
