package org.skylershelvin.SAMABackened.API.security;


import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.skylershelvin.SAMABackened.model.LocalUser;
import org.skylershelvin.SAMABackened.model.dao.LocalUserDAO;
import org.skylershelvin.SAMABackened.service.JWTService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private LocalUserDAO localUserDAO;

    public JwtRequestFilter(JWTService jwtService, LocalUserDAO localUserDAO) {
        this.jwtService = jwtService;
        this.localUserDAO = localUserDAO;
    }

    /**
     * Request comes in and we began to decode it to find the user by username
     * if client is not authenticated they wont be able to see security layer
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String tokenHeader = request.getHeader("Authorization");
       //first check token to see if header starts with Bearer
       if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
           String token = tokenHeader.substring(7);
           try {
               String username = jwtService.getUsername(token);
               Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(username);
               if (opUser.isPresent()){
                   LocalUser user = opUser.get();
                   UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }

           } catch (JWTDecodeException ex){

           }
       }
        //channel through filers
        filterChain.doFilter(request, response);
    }
}
