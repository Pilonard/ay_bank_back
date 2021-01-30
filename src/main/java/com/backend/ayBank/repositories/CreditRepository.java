package com.backend.ayBank.repositories;

import com.backend.ayBank.entities.CreditEntity;
import com.backend.ayBank.shared.dto.CreditDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends CrudRepository<CreditEntity, Long> {
    CreditEntity findByIdCredit(String idCredit);
}
