package com.backend.ayBank.services;

import com.backend.ayBank.shared.dto.CreditDto;
import com.backend.ayBank.shared.dto.UserDto;

public interface CreditService {
    CreditDto createCredit(CreditDto creditDto);
    CreditDto adminUpdateCredit(String idCredit, CreditDto creditDto);


}
