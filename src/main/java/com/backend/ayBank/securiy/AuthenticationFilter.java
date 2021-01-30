package com.backend.ayBank.securiy;

import com.backend.ayBank.SpringApplicationContext;
import com.backend.ayBank.services.UserService;
import com.backend.ayBank.shared.dto.UserDto;
import io.jsonwebtoken.Jwts;
import com.backend.ayBank.requests.UserLoginRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter  extends UsernamePasswordAuthenticationFilter {
    public final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    /*
    this method will executed when we have post request with /login
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserLoginRequest  credentiel = new ObjectMapper().readValue(request.getInputStream(),UserLoginRequest.class);
//             verify in DB if we have a user with this email and password if yes the method successfull with be executed
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentiel.getEmail(),credentiel.getPassword(),new ArrayList<>())
            );

        } catch (IOException e) {
          throw new RuntimeException();
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
       String userName = ((User) authResult.getPrincipal()).getUsername();
        //        token contain username experation date and hashed
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET )
                .compact();
//         the name of de classe in the method getBean should lowerCase
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUser(userName);
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader("user_id", userDto.getUserId());
//        res.getWriter().write("{\"token\""+token+"\", \"id\"; \"");
    }
}
