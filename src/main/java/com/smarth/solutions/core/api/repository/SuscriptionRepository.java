package com.smarth.solutions.core.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarth.solutions.core.api.model.Suscription;

@Repository
public interface SuscriptionRepository extends JpaRepository<Suscription, Long> {
}
