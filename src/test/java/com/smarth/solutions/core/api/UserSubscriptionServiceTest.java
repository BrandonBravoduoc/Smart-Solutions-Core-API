package com.smarth.solutions.core.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.javafaker.Faker;
import com.smarth.solutions.core.api.dto.UserSubscriptionDTO;
import com.smarth.solutions.core.api.model.entity.Subscription;
import com.smarth.solutions.core.api.model.entity.UserSubscription;
import com.smarth.solutions.core.api.model.enums.SubscriptionStatus;
import com.smarth.solutions.core.api.repository.UserSubscriptionRepository;
import com.smarth.solutions.core.api.service.SubscriptionHistoryService;
import com.smarth.solutions.core.api.service.SubscriptionService;
import com.smarth.solutions.core.api.service.UserSubscriptionService;
import com.smarth.solutions.core.api.util.Validations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserSubscriptionServiceTest {

    @Mock private UserSubscriptionRepository userSubscriptionRepository;
    @Mock private SubscriptionService subscriptionService;
    @Mock private SubscriptionHistoryService historyService;
    @Mock private Validations validations;

    @InjectMocks private UserSubscriptionService userSubscriptionService;

    private final Faker faker = new Faker();

    private Subscription testPlan;
    private UserSubscription activeSubscription;

    @BeforeEach
    void setUp() {
        testPlan = new Subscription();
        testPlan.setId(1L);
        testPlan.setName("Plan Basic");
        testPlan.setDetails("Acceso básico al gimnasio");
        testPlan.setPrice(new BigDecimal("9990"));
        testPlan.setDurationMonths(1);
        testPlan.setActive(true);

        activeSubscription = new UserSubscription();
        activeSubscription.setId(1L);
        activeSubscription.setUserId(100L);
        activeSubscription.setSubscription(testPlan);
        activeSubscription.setStatus(SubscriptionStatus.ACTIVE);
        activeSubscription.setCancelAtPeriodEnd(false);
        activeSubscription.setStartDate(LocalDateTime.now().minusMonths(1));
        activeSubscription.setCurrentPeriodStart(LocalDateTime.now().minusMonths(1));
        activeSubscription.setCurrentPeriodEnd(LocalDateTime.now().plusMonths(2));
    }

    @Test
    void obtener_suscripcion() {
        when(userSubscriptionRepository.findByUserId(100L)).thenReturn(Optional.of(activeSubscription));

        Optional<UserSubscriptionDTO.Response> result =
                userSubscriptionService.getSubscriptionDtoByUserId(100L);

        assertTrue(result.isPresent());
        assertEquals("ACTIVE", result.get().status());
        assertEquals(100L, result.get().userId());
    }

    @Test
    void usuario_sin_plan() {
        when(userSubscriptionRepository.findByUserId(999L)).thenReturn(Optional.empty());

        Optional<UserSubscriptionDTO.Response> result =
                userSubscriptionService.getSubscriptionDtoByUserId(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void todas_las_suscripciones() {
        long randomUserId = faker.number().numberBetween(201L, 999L);

        UserSubscription secondSub = new UserSubscription();
        secondSub.setId(2L);
        secondSub.setUserId(randomUserId);
        secondSub.setSubscription(testPlan);
        secondSub.setStatus(SubscriptionStatus.ACTIVE);
        secondSub.setCancelAtPeriodEnd(false);
        secondSub.setStartDate(LocalDateTime.now().minusDays(15));
        secondSub.setCurrentPeriodStart(LocalDateTime.now().minusDays(15));
        secondSub.setCurrentPeriodEnd(LocalDateTime.now().plusDays(15));

        when(userSubscriptionRepository.findAll()).thenReturn(List.of(activeSubscription, secondSub));

        List<UserSubscriptionDTO.Response> result = userSubscriptionService.getAllSubscriptions();

        assertEquals(2, result.size());
    }

    @Test
    void cancelar_suscripcion() {
        when(userSubscriptionRepository.findByUserId(100L)).thenReturn(Optional.of(activeSubscription));
        when(userSubscriptionRepository.save(activeSubscription)).thenReturn(activeSubscription);

        UserSubscriptionDTO.Response result = userSubscriptionService.cancelRenewal(100L);

        assertEquals("CANCELED", result.status());
        assertTrue(result.cancelAtPeriodEnd());
        verify(userSubscriptionRepository).save(activeSubscription);
    }

    @Test
    void estado_tras_cancelacion() {
        when(userSubscriptionRepository.findByUserId(100L)).thenReturn(Optional.of(activeSubscription));
        when(userSubscriptionRepository.save(activeSubscription)).thenReturn(activeSubscription);

        UserSubscriptionDTO.Response result = userSubscriptionService.cancelRenewal(100L);

        assertEquals("EXPIRED", result.status()); // falla: el estado real es CANCELED
    }
}
