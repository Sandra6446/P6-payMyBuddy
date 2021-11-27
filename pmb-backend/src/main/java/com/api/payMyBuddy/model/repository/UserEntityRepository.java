package com.api.payMyBuddy.model.repository;

import com.api.payMyBuddy.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

        public Optional<UserEntity> findByEmail(String email);

}
