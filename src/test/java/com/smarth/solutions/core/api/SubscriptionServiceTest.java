package com.smarth.solutions.core.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.github.javafaker.Faker;
import com.smarth.solutions.core.api.dto.SubscriptionDTO;
import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.repository.SubscriptionRepository;
import com.smarth.solutions.core.api.service.SubscriptionService;
import com.smarth.solutions.core.api.util.Validations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock private SubscriptionRepository subscriptionRepository;
    @Mock private Validations validations;

    @InjectMocks private SubscriptionService subscriptionService;

    private final Faker faker = new Faker();

    private Subscription activePlan;
    private Subscription inactivePlan;

    @BeforeEach
    void setUp() {
        activePlan = new Subscription();
        activePlan.setId(1L);
        activePlan.setName("Plan Premium");
        activePlan.setDetails("Acceso completo al gimnasio con clases incluidas");
        activePlan.setPrice(new BigDecimal("29990"));
        activePlan.setDurationMonths(3);
        activePlan.setActive(true);

        inactivePlan = new Subscription();
        inactivePlan.setId(2L);
        inactivePlan.setName("Plan Antiguo");
        inactivePlan.setDetails("Plan descontinuado");
        inactivePlan.setPrice(new BigDecimal("9990"));
        inactivePlan.setDurationMonths(1);
        inactivePlan.setActive(false);
    }

    @Test
    void planes_disponibles() {
        when(subscriptionRepository.findByIsActiveTrue()).thenReturn(List.of(activePlan));

        List<SubscriptionDTO.Response> result = subscriptionService.getAllActivePlans();

        assertEquals(1, result.size());
        assertEquals("Plan Premium", result.get(0).name());
        assertTrue(result.get(0).isActive());
    }

    @Test
    void registrar_nuevo_plan() {
        String planName = faker.commerce().productName();
        SubscriptionDTO.Request request = new SubscriptionDTO.Request(
                planName,
                "Plan generado por prueba automática",
                new BigDecimal("19990"),
                2,
                true
        );

        Subscription saved = new Subscription();
        saved.setId(10L);
        saved.setName(planName);
        saved.setDetails("Plan generado por prueba automática");
        saved.setPrice(new BigDecimal("19990"));
        saved.setDurationMonths(2);
        saved.setActive(true);

        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(saved);

        SubscriptionDTO.Response result = subscriptionService.createPlan(request);

        assertNotNull(result);
        assertEquals(planName, result.name());
        assertEquals(new BigDecimal("19990"), result.price());
        assertTrue(result.isActive());
    }

    @Test
    void actualizar_sin_datos() {
        assertThrows(IllegalArgumentException.class,
                () -> subscriptionService.updatePlan(1L, null));
    }

    @Test
    void plan_no_registrado() {
        when(subscriptionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> subscriptionService.getPlanResponseById(999L));
    }

    @Test
    void eliminar_plan() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(activePlan));

        subscriptionService.deletePlan(1L);

        verify(subscriptionRepository, times(1)).delete(activePlan);
    }

    @Test
    void plan_creado_correctamente() {
        String realName = faker.commerce().productName();
        SubscriptionDTO.Request request = new SubscriptionDTO.Request(
                realName,
                "Descripción de prueba",
                new BigDecimal("14990"),
                1,
                true
        );

        Subscription saved = new Subscription();
        saved.setName(realName);
        saved.setDetails("Descripción de prueba");
        saved.setPrice(new BigDecimal("14990"));
        saved.setDurationMonths(1);
        saved.setActive(true);

        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(saved);

        SubscriptionDTO.Response result = subscriptionService.createPlan(request);

        assertEquals("Plan Inexistente XYZ", result.name()); // falla: el nombre real es distinto
    }
}
