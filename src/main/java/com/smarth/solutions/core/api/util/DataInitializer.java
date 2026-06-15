package com.smarth.solutions.core.api.util;

import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component 
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void run(String... args) throws Exception {
        
        if (subscriptionRepository.count() == 0) {
            log.info("Base de datos vacía. Inicializando planes de suscripción por defecto para el Gimnasio...");

            Subscription planMensual = new Subscription();
            planMensual.setName("Plan Mensual");
            planMensual.setDetails("Acceso completo a las instalaciones de pesas y cardio durante 1 mes.");
            planMensual.setPrice(new BigDecimal("29.99"));
            planMensual.setDurationMonths(1);
            planMensual.setActive(true);

            Subscription planSemestral = new Subscription();
            planSemestral.setName("Plan Semestral");
            planSemestral.setDetails("Ahorra pagando 6 meses por adelantado. Incluye evaluación física gratuita.");
            planSemestral.setPrice(new BigDecimal("149.99"));
            planSemestral.setDurationMonths(6);
            planSemestral.setActive(true);

            Subscription planAnual = new Subscription();
            planAnual.setName("Plan Anual VIP");
            planAnual.setDetails("El mejor valor. 12 meses de acceso total, clases grupales y consulta nutricional.");
            planAnual.setPrice(new BigDecimal("250.00"));
            planAnual.setDurationMonths(12);
            planAnual.setActive(true);

            subscriptionRepository.saveAll(List.of(planMensual, planSemestral, planAnual));
            
            log.info("¡Planes por defecto creados exitosamente!");
        } else {
            log.info("Los planes de suscripción ya existen en la base de datos. Saltando inicialización.");
        }
    }
}
