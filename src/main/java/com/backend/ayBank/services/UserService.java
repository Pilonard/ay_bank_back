package com.backend.ayBank.services;

import com.backend.ayBank.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserId(String userId);
    List<UserDto> getAllUser();


}
