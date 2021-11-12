package com.api.payMyBuddy.model.repository;

import com.api.payMyBuddy.model.entity.NetworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionEntityRepository extends JpaRepository<NetworkEntity, String> {
}
