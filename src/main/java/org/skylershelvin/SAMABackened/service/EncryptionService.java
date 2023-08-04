package org.skylershelvin.SAMABackened.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class  EncryptionService {
    // increasing the layers of security
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    private String salt;

    @PostConstruct
    //once we can PostConstruct salt wounds has already been written.
    public void postConstruct(){
        salt= BCrypt.gensalt(saltRounds);
    }
    //easy to change in future and upgrade
    public String encryptPassword(String password){
        return BCrypt.hashpw(password, salt);
    }

    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }

    //ensure rounds in application.properties

}
