package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.configuration.PlayerStakeThresholdProperties;
import com.sergiomartinrubio.stakeaccumulatorservice.messaging.PlayerStakeAlertProducer;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PlayerStakeNotificationServiceTest {

    private static final Long ACCOUNT_ID = 123L;
    private static final BigDecimal AMOUNT_THRESHOLD = new BigDecimal(100);
    private static final int SIXTY_MINUTES = 60;

    @Mock
    private PlayerStakeRepository playerStakeRepository;

    @Mock
    private PlayerStakeThresholdProperties playerStakeThresholdProperties;

    @Mock
    private PlayerStakeAlertProducer playerStakeAlertProducer;

    @Mock
    private PlayerStakeAlertRepository playerStakeAlertRepository;

    @InjectMocks
    private PlayerStakeNotificationService playerStakeNotificationService;

    @Test
    void shouldNotSendAlertWhenPlayerStakeIsUnder100() {
        // GIVEN
        given(playerStakeRepository.getTotalStakeByAccountAndTimeWindowThreshold(ACCOUNT_ID, SIXTY_MINUTES))
                .willReturn(Optional.of(BigDecimal.valueOf(80)));
        given(playerStakeThresholdProperties.getAmount()).willReturn(AMOUNT_THRESHOLD);
        given(playerStakeThresholdProperties.getTimeWindowInMinutes()).willReturn(SIXTY_MINUTES);

        // WHEN
        playerStakeNotificationService.evaluate(ACCOUNT_ID);

        // THEN
        then(playerStakeAlertProducer).shouldHaveNoInteractions();
        then(playerStakeAlertRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldSendAlertWhenPlayerStakeIsUnder100() {
        // GIVEN
        given(playerStakeRepository.getTotalStakeByAccountAndTimeWindowThreshold(ACCOUNT_ID, SIXTY_MINUTES))
                .willReturn(Optional.of(BigDecimal.valueOf(130)));
        given(playerStakeThresholdProperties.getAmount()).willReturn(AMOUNT_THRESHOLD);
        given(playerStakeThresholdProperties.getTimeWindowInMinutes()).willReturn(SIXTY_MINUTES);

        // WHEN
        playerStakeNotificationService.evaluate(ACCOUNT_ID);

        // THEN
        then(playerStakeAlertProducer).should().sendMessage(new PlayerStakeAlertMessage(ACCOUNT_ID, new BigDecimal(130)));
        then(playerStakeAlertRepository).should().save(any(PlayerStakeAlertEntity.class));
    }

}