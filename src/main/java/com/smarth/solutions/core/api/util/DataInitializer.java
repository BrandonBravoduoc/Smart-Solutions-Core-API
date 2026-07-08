package com.smarth.solutions.core.api.util;

import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.enums.ServiceType;
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

            Long defaultAddressId = 1L;

            Subscription planMensual = new Subscription();
            planMensual.setName("Plan Mensual");
            planMensual.setDetails("Acceso completo a las instalaciones de pesas y cardio durante 1 mes.");
            planMensual.setPrice(new BigDecimal("30000"));
            planMensual.setDurationMonths(1);
            planMensual.setActive(true);
            planMensual.setServiceType(ServiceType.PRESENCIAL);
            planMensual.setAddressId(defaultAddressId);

            Subscription planSemestral = new Subscription();
            planSemestral.setName("Plan Semestral");
            planSemestral.setDetails("Ahorra pagando 6 meses por adelantado. Incluye evaluación física gratuita.");
            planSemestral.setPrice(new BigDecimal("150000"));
            planSemestral.setDurationMonths(6);
            planSemestral.setActive(true);
            planSemestral.setServiceType(ServiceType.PRESENCIAL);
            planSemestral.setAddressId(defaultAddressId);

            Subscription planAnual = new Subscription();
            planAnual.setName("Plan Anual VIP");
            planAnual.setDetails("El mejor valor. 12 meses de acceso total, clases grupales y consulta nutricional.");
            planAnual.setPrice(new BigDecimal("250000"));
            planAnual.setDurationMonths(12);
            planAnual.setActive(true);
            planAnual.setServiceType(ServiceType.PRESENCIAL);
            planAnual.setAddressId(defaultAddressId);

            subscriptionRepository.saveAll(List.of(planMensual, planSemestral, planAnual));
            
            log.info("¡Planes por defecto creados exitosamente!");
        } else {
            log.info("Los planes de suscripción ya existen en la base de datos. Saltando inicialización.");
        }
    }
}
