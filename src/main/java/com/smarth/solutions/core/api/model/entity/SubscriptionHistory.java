package com.smarth.solutions.core.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "subscription_history")
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;




}
