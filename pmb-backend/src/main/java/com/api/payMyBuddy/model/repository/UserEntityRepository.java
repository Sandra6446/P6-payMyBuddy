package com.api.payMyBuddy.model.repository;

import com.api.payMyBuddy.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Reads and updates users entity in database
 *
 * @see UserEntity
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

}
