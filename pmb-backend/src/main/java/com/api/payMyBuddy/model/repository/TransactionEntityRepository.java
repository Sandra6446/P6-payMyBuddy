package com.api.payMyBuddy.model.repository;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Reads and updates transactions entity in database
 *
 * @see TransactionEntity
 */
@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, String> {

}
