package com.api.payMyBuddy.model.repository;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Reads and updates connections entity in database
 *
 * @see ConnectionEntity
 */
@Repository
public interface ConnectionEntityRepository extends JpaRepository<ConnectionEntity, String> {

}
