package org.skylershelvin.SAMABackened.API.security;


import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.skylershelvin.SAMABackened.model.LocalUser;
import org.skylershelvin.SAMABackened.model.dao.LocalUserDAO;
import org.skylershelvin.SAMABackened.service.JWTService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter implements ChannelInterceptor {

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
      UsernamePasswordAuthenticationToken token = checkToken(tokenHeader);
      if(token != null){
          token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      }
        //channel through filers
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken checkToken (String token){
        //first check token to see if header starts with Bearer
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(username);
                if (opUser.isPresent()){
                    LocalUser user = opUser.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return authentication;
                }

            } catch (JWTDecodeException ex){

            }
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        return null;
    }
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        SimpMessageType messageType =
                (SimpMessageType) message.getHeaders().get("simpMessageType");
        if (message.getHeaders().get("simpMessageType").equals(SimpMessageType.SUBSCRIBE)
            || messageType.equals(SimpMessageType.MESSAGE)) {
            Map nativeHeaders = (Map) message.getHeaders().get("nativeHeaders");
            if (nativeHeaders != null) {
                List authTokenList = (List) nativeHeaders.get("Authorization");
                if (authTokenList != null) {
                    String tokenHeader = (String) authTokenList.get(0);
                    checkToken(tokenHeader);
                }

            }
        }
        return message;
    }
}
