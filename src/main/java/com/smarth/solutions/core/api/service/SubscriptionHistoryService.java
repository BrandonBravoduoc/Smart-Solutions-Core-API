package com.smarth.solutions.core.api.service;

import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.entity.SubscriptionHistory;
import com.smarth.solutions.core.api.model.enums.HistoryAction;
import com.smarth.solutions.core.api.repository.SubscriptionHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionHistoryService {

    @Autowired
    private SubscriptionHistoryRepository historyRepository;

    @Transactional
    public void recordEvent(Long userId, Subscription plan, LocalDateTime start, LocalDateTime end, HistoryAction action) {
        SubscriptionHistory history = new SubscriptionHistory();
        history.setUserId(userId);
        history.setSubscriptionId(plan.getId());
        history.setSubscriptionName(plan.getName());
        history.setSubscriptionPrice(plan.getPrice());
        history.setPeriodStart(start);
        history.setPeriodEnd(end);
        history.setAction(action);
        history.setChangedAt(LocalDateTime.now());
        
        historyRepository.save(history);
    }

    public List<SubscriptionHistory> getHistoryByUserId(Long userId) {
        return historyRepository.findByUserIdOrderByChangedAtDesc(userId);
    }
}
