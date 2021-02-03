package com.backend.ayBank.services.impl;

import com.backend.ayBank.entities.CreditEntity;
import com.backend.ayBank.entities.UserEntity;
import com.backend.ayBank.repositories.CreditRepository;
import com.backend.ayBank.repositories.UserRepository;
import com.backend.ayBank.services.CreditService;
import com.backend.ayBank.shared.dto.CreditDto;
import com.backend.ayBank.shared.dto.UserDto;
import com.backend.ayBank.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@Service
public class CreditServiceImpl implements CreditService {
    @Autowired
    CreditRepository creditRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public CreditDto createCredit(CreditDto credit, String email) {
        UserEntity currentUser = userRepository.findByEmail(email);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(currentUser,UserDto.class);
        credit.setIdCredit(UUID.randomUUID().toString().substring(0,20));
        credit.setUser(userDto);
        CreditEntity creditEntity = modelMapper.map(credit,CreditEntity.class);
//        BeanUtils.copyProperties(credit,creditEntity);
        CreditEntity creditE = creditRepository.save(creditEntity);
        CreditDto creditDto = modelMapper.map(creditE,CreditDto.class);
//        BeanUtils.copyProperties(creditE, creditDto);
        return  creditDto;
    }

    @Override
    public CreditDto adminUpdateCredit( CreditDto credit) {
        System.out.println("hello i am admin updateCredit" + credit.getCreditState());
//        CreditEntity creditEntity = creditRepository.findByIdCredit(idCredit);
        CreditEntity creditEntity = creditRepository.findByIdCredit(credit.getIdCredit());
        System.out.println("credit entity is enmpty ?" + creditEntity);
        if(creditEntity == null) throw new UsernameNotFoundException(credit.getIdCredit());
        creditEntity.setCreditState(credit.getCreditState());
        System.out.println("new value of type credit" + credit.getCreditState());

        creditRepository.save(creditEntity);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        CreditDto creditDto = modelMapper.map(creditEntity,CreditDto.class);

//        BeanUtils.copyProperties(creditEntity, creditDto);
        return  creditDto;
    }

    @Override
    public List<CreditDto> getAllCredit(String email) {

        UserEntity currentUser = userRepository.findByEmail(email);
        List<CreditEntity> credits = currentUser.getAdmin() ==true ?
                (List<CreditEntity>) creditRepository.findAll():
                creditRepository.findByUser(currentUser)
                ;
        Type listType = new TypeToken<List<CreditDto>>(){}.getType();
        List<CreditDto> creditDtos = ModelMapperUtil.modelMapper().map(credits,listType);

        return creditDtos;
    }
}
