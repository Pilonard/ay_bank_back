package com.backend.ayBank.controllers;

import com.backend.ayBank.requests.UserRequest;
import com.backend.ayBank.responses.CreditResponse;
import com.backend.ayBank.responses.UserResponse;
import com.backend.ayBank.services.UserService;
import com.backend.ayBank.shared.dto.CreditDto;
import com.backend.ayBank.shared.dto.UserDto;
import com.backend.ayBank.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {



    @Autowired
    UserService userService;

    String[] imageProfile = {
            "https://i.pinimg.com/originals/8c/2a/6c/8c2a6cc725c8fd531880541c0afee157.jpg",
            "https://acegif.com/wp-content/uploads/2020/11/am0ngsusxh-21-gap.jpg",
            "https://avatars.githubusercontent.com/u/9919?s=200&v=4",
            "https://i.pinimg.com/originals/f5/1d/08/f51d08be05919290355ac004cdd5c2d6.png",
            "https://pbs.twimg.com/profile_images/805270298585137156/csKNl_fn_400x400.jpg"
    };
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
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUser( ) {
        List<UserDto> userDtoList = userService.getAllUser();
        Type listType = new TypeToken<List<UserResponse>>() {}.getType();
        List<UserResponse> listUsers = ModelMapperUtil.modelMapper().map(userDtoList, listType);
        return new ResponseEntity<>(listUsers,HttpStatus.OK);

    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> updateUser(String userId ){

        return new ResponseEntity<>(new UserResponse(), HttpStatus.ACCEPTED); //202
    }

    @PostMapping
    public ResponseEntity<UserResponse> postUser(@RequestBody UserRequest userRequest){
        //Model mapper lorsqu'on a une type complex ,model mapper pour le relier avec une autre class

        /*
        initialise admin in userRequest
         */
        userRequest.setImgProfile(imageProfile[new Random().nextInt(imageProfile.length-1)]);
        userRequest.setAdmin(false);
        if(userRequest.getImgProfile().isEmpty()) userRequest.setImgProfile("https://i.redd.it/1d986et5imy51.jpg");
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
