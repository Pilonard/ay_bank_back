package com.backend.ayBank.controllers;

import com.backend.ayBank.requests.UserRequest;
import com.backend.ayBank.responses.UserResponse;
import com.backend.ayBank.services.UserService;
import com.backend.ayBank.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;

    }
    @Autowired
    UserService userService;
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id ){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = userService.getUserId(id);
        UserResponse userResponse =modelMapper.map(userDto,UserResponse.class);
//        BeanUtils.copyProperties(userDto, userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK); //200
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> updateUser(String userId ){

        return new ResponseEntity<>(new UserResponse(), HttpStatus.ACCEPTED); //202
    }
    @PostMapping
    public ResponseEntity<UserResponse> postUser(@RequestBody UserRequest userRequest){
        //Model mapper lorsqu'on a une type complex ,model mapper pour le relier avec une autre class

        /*
        Presentation Layer
         */

        ModelMapper modelMapper =  new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);


        UserDto userDto = modelMapper.map(userRequest,UserDto.class);


        System.out.println(userDto.toString());
//        UserDto  userDto = new UserDto();
//        BeanUtils.copyProperties(userRequest,userDto);
        /*
             service (insert into data base)
         */
        UserDto createUser = userService.createUser(userDto);

        UserResponse userResponse = modelMapper.map(createUser,UserResponse.class);
//        BeanUtils.copyProperties(createUser,userResponse);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED); //201
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String id){
        return new ResponseEntity<>(new UserResponse(), HttpStatus.NO_CONTENT); //204
    }


}
