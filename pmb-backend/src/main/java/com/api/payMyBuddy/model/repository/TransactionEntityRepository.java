package com.api.payMyBuddy.model.repository;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, String> {

}
