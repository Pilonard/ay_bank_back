package com.backend.ayBank.services;

import com.backend.ayBank.shared.dto.CreditDto;
import com.backend.ayBank.shared.dto.UserDto;

import java.util.List;

public interface CreditService {
    CreditDto createCredit(CreditDto creditDto, String email);
    CreditDto adminUpdateCredit(CreditDto creditDto);
    List<CreditDto> getAllCredit( String email);

}
