package com.backend.ayBank.services.impl;

import com.backend.ayBank.entities.UserEntity;
import com.backend.ayBank.repositories.UserRepository;
import com.backend.ayBank.services.UserService;
import com.backend.ayBank.shared.dto.CreditDto;
import com.backend.ayBank.shared.dto.UserDto;
import com.backend.ayBank.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail())!=null) throw  new RuntimeException("User already exist");
        LinkedList<CreditDto> creditList = new LinkedList<>();
        for (CreditDto credit: user.getCredits()) {
            CreditDto creditDto = credit;
            creditDto.setUser(user);
            creditDto.setIdCredit(UUID.randomUUID().toString().substring(0,20));

            creditList.add(creditDto);
        }
        user.setCredits(creditList);
        //System.out.println(user.toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        userEntity.setUserId(UUID.randomUUID().toString().substring(0,20));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        UserEntity userE = userRepository.save(userEntity);

        UserDto userDto = modelMapper.map(userE,UserDto.class);


        return userDto;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
//        UserDto userDto =modelMapper.map(userEntity,UserDto.class);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return  userDto;
    }

    @Override
    public UserDto getUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userEntity,UserDto.class);
//        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUser() {
        /*
        get user without admin
        List<UserEntity> users = userRepository.findAllByAdmin(false);
         */
        /*
        get Users and admins
         */
        List<UserEntity> users = userRepository.findAll();
        if(users == null) throw new RuntimeException();
        Type listType = new TypeToken<List<UserDto>>(){}.getType();
        List<UserDto> userDtos = ModelMapperUtil.modelMapper().map(users,listType);

        return userDtos;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity=userRepository.findByEmail(email);
        if(userEntity == null ) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
