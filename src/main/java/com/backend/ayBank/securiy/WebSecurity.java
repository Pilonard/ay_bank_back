package com.backend.ayBank.securiy;

import com.backend.ayBank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@EnableWebSecurity
public class WebSecurity  extends WebSecurityConfigurerAdapter {
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Autowired
//    UserService userDetailService;

    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserService userDetailService;
    public  WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder,UserService userService){
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.userDetailService=userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers("/credit/**").permitAll()
                .antMatchers(HttpMethod.POST,"/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthenticationFilter()) // the attemp authenticaterFilt will be executed
                .addFilter(new AuthorizationFilter(authenticationManager()));
    }

    protected AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter =new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/user/login");
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}
