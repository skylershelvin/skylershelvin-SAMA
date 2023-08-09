package org.skylershelvin.SAMABackened.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.skylershelvin.SAMABackened.model.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
responsible for handling the encoded strings to authenticate users; makes it unique to each user
 */
@Service
public class JWTService {
    //@Autowired
    public JWTService() {
    }

    //credential verifier so we can identify users using encryption
// secret key
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    // ensures that SAMA has given the user the key.
    @Value("${jwt.issuer}")
    private String issuer;

    //Expiring date of the key
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private Algorithm algorithm;

    private static final String USERNAME_KEY = "USERNAME";

    //private static final String RESET_PASSWORD_EMAIL_KEY;


    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generationJWT(LocalUser user){
        return JWT.create()
                .withClaim("USERNAME", user.getUsername())
                //expires in x amount of time
                .withExpiresAt(new Date(System.currentTimeMillis() * (1000L * expiryInSeconds)))
                // who issued it
                .withIssuer(issuer)
                //knows who the user is
                .sign(algorithm);
    }


public String getUsername(String token){
        //getting the username out of the token and returns as a string
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
}

/*public String generatePasswordResetJWT(LocalUser user){
        return JWT.create()
                .withClaim(RESET_PASSWORD_EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                .withIssuer(issuer)
                .sign(algorithm);
    }*/

    public String getPasswordEMail(String token){
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(USERNAME_KEY).asString();
    }

}


