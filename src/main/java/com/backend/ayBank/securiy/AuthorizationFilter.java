package com.backend.ayBank.securiy;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        verify if the request has a header Authorization and verify if the header is null or not contains a Bearer prefix.
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if(header == null || !header.contains(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken userAuthentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        chain.doFilter(request,response);




    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){

        String token =request.getHeader(SecurityConstants.HEADER_STRING);
        if(token !=null){
            token = token.replace(SecurityConstants.TOKEN_PREFIX,"");


            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.TOKEN_SECRET) // return user object if the token is generate with the same secret key
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if(user != null) {

                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }

            return null;
        }

        return null;

    }


}
