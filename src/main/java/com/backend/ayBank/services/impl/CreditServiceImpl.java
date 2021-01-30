package com.backend.ayBank.services.impl;

import com.backend.ayBank.entities.CreditEntity;
import com.backend.ayBank.entities.UserEntity;
import com.backend.ayBank.repositories.CreditRepository;
import com.backend.ayBank.services.CreditService;
import com.backend.ayBank.shared.dto.CreditDto;
import com.backend.ayBank.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService {
    @Autowired
    CreditRepository creditRepository;
    @Override
    public CreditDto createCredit(CreditDto credit) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        CreditEntity creditEntity = modelMapper.map(credit,CreditEntity.class);
//        BeanUtils.copyProperties(credit,creditEntity);
        CreditEntity creditE = creditRepository.save(creditEntity);
        CreditDto creditDto = modelMapper.map(creditE,CreditDto.class);
//        BeanUtils.copyProperties(creditE, creditDto);
        return  creditDto;
    }

    @Override
    public CreditDto adminUpdateCredit(String idCredit, CreditDto credit) {
        CreditEntity creditEntity = creditRepository.findByIdCredit(idCredit);
        if(creditEntity == null) throw new UsernameNotFoundException(idCredit);
        creditEntity.setTypeCredit(credit.getTypeCredit());

        creditRepository.save(creditEntity);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        CreditDto creditDto = modelMapper.map(creditEntity,CreditDto.class);

//        BeanUtils.copyProperties(creditEntity, creditDto);
        return  creditDto;
    }
}
