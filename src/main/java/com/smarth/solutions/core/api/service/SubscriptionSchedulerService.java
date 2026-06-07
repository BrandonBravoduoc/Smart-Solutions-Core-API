package com.smarth.solutions.core.api.service;

import com.smarth.solutions.core.api.model.entity.UserSubscription;
import com.smarth.solutions.core.api.model.enums.HistoryAction;
import com.smarth.solutions.core.api.model.enums.SubscriptionStatus;
import com.smarth.solutions.core.api.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionSchedulerService {

    private final UserSubscriptionRepository userRepository;
    private final SubscriptionHistoryService historyService;
    private final CacheManager cacheManager; 


    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void checkAndExpireSubscriptions() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Iniciando tarea automática de verificación de expiraciones a las {}", now);

        try {
            List<UserSubscription> expiredSubscriptions = userRepository
                    .findByCurrentPeriodEndBeforeAndStatusIn(now, List.of(SubscriptionStatus.CANCELED, SubscriptionStatus.PAST_DUE));

            if (expiredSubscriptions.isEmpty()) {
                log.info("No se encontraron suscripciones expiradas en esta ejecución.");
                return;
            }

            log.info("Se encontraron {} suscripciones expiradas. Procediendo a dar de baja.", expiredSubscriptions.size());

            for (UserSubscription sub : expiredSubscriptions) {
                try {
                    sub.setStatus(SubscriptionStatus.EXPIRED);
                    userRepository.save(sub);

                    historyService.recordEvent(
                            sub.getUserId(),
                            sub.getSubscription(),
                            sub.getCurrentPeriodStart(),
                            sub.getCurrentPeriodEnd(),
                            HistoryAction.EXPIRATION
                    );

                    var cache = cacheManager.getCache("user_subscription_dto");
                    if (cache != null) {
                        cache.evict(sub.getUserId());
                        log.info("Caché de Redis desalojada para el usuario ID: {}", sub.getUserId());
                    }
                } catch (Exception e) {
                    log.error("Error procesando expiración para usuario ID: {}", sub.getUserId(), e);
                }
            }
            log.info("Tarea de expiración completada exitosamente.");
        } catch (Exception e) {
            log.error("Error en tarea automática de verificación de expiraciones", e);
        }
    }
}
