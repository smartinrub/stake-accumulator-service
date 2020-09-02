package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.configuration.PlayerStakeThresholdProperties;
import com.sergiomartinrubio.stakeaccumulatorservice.messaging.PlayerStakeAlertProducer;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlert;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PlayerStakeNotificationServiceTest {

    private static final Long ACCOUNT_ID = 123L;
    private static final UUID PLAYER_STAKE_ID_1 = UUID.randomUUID();
    private static final UUID PLAYER_STAKE_ID_2 = UUID.randomUUID();
    private static final UUID PLAYER_STAKE_ID_3 = UUID.randomUUID();
    private static final UUID PLAYER_STAKE_ID_4 = UUID.randomUUID();
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
        PlayerStake firstPlayerStake = createPlayerStakeEntity(PLAYER_STAKE_ID_1, new BigDecimal(40));
        PlayerStake secondPlayerStake = createPlayerStakeEntity(PLAYER_STAKE_ID_2, new BigDecimal(40));
        given(playerStakeRepository.findAllByAccountAndTimeWindowThreshold(ACCOUNT_ID, SIXTY_MINUTES))
                .willReturn(Set.of(firstPlayerStake, secondPlayerStake));
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
        PlayerStake firstPlayerStake = createPlayerStakeEntity(PLAYER_STAKE_ID_1, new BigDecimal(40));
        PlayerStake secondPlayerStake = createPlayerStakeEntity(PLAYER_STAKE_ID_2, new BigDecimal(40));
        PlayerStake thirdPlayerStake = createPlayerStakeEntity(PLAYER_STAKE_ID_3, new BigDecimal(10));
        PlayerStake fourthPlayerStake = createPlayerStakeEntity(PLAYER_STAKE_ID_4, new BigDecimal(40));
        Set<PlayerStake> stakes = Set.of(
                firstPlayerStake,
                secondPlayerStake,
                thirdPlayerStake,
                fourthPlayerStake);
        given(playerStakeRepository.findAllByAccountAndTimeWindowThreshold(ACCOUNT_ID, SIXTY_MINUTES)).willReturn(stakes);
        given(playerStakeThresholdProperties.getAmount()).willReturn(AMOUNT_THRESHOLD);
        given(playerStakeThresholdProperties.getTimeWindowInMinutes()).willReturn(SIXTY_MINUTES);

        // WHEN
        playerStakeNotificationService.evaluate(ACCOUNT_ID);

        // THEN
        then(playerStakeAlertProducer).should().sendMessage(new PlayerStakeAlertMessage(ACCOUNT_ID, new BigDecimal(130)));
        then(playerStakeAlertRepository).should().save(any(PlayerStakeAlert.class));
    }

    private PlayerStake createPlayerStakeEntity(UUID playerStakeId, BigDecimal stake) {
        return PlayerStake.builder()
                .id(playerStakeId)
                .accountId(ACCOUNT_ID)
                .stake(stake)
                .creationDateTime(LocalDateTime.now())
                .build();
    }

}