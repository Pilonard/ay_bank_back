package com.backend.ayBank.repositories;

import com.backend.ayBank.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    List<UserEntity> findAllByAdmin(Boolean admin);
    List<UserEntity> findAll();

}
