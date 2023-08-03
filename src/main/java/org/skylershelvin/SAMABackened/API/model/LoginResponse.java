package org.skylershelvin.SAMABackened.API.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    // created to reduce massive amounts of code changes in future
    //Json object
    private String jwt;
}
