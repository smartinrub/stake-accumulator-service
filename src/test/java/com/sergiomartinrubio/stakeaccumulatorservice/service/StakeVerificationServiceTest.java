package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.configuration.PlayerStakeThresholdProperties;
import com.sergiomartinrubio.stakeaccumulatorservice.messaging.PlayerStakeAlertProducer;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class StakeVerificationServiceTest {

    private static final Long ACCOUNT_ID = 123L;
    private static final UUID PLAYER_STAKE_ID_1 = UUID.randomUUID();
    private static final UUID PLAYER_STAKE_ID_2 = UUID.randomUUID();
    private static final UUID PLAYER_STAKE_ID_3 = UUID.randomUUID();
    private static final UUID PLAYER_STAKE_ID_4 = UUID.randomUUID();
    private static final BigDecimal AMOUNT_THRESHOLD = new BigDecimal(100);
    private static final int ONE_HOUR = 1;

    @Mock
    private PlayerStakeRepository playerStakeRepository;

    @Mock
    private PlayerStakeThresholdProperties playerStakeThresholdProperties;

    @Mock
    private PlayerStakeAlertProducer playerStakeAlertProducer;

    @InjectMocks
    private StakeVerificationService stakeVerificationService;

    @Test
    void shouldNotSendAlertWhenPlayerStakeIsUnder100() {
        // GIVEN
        PlayerStakeEntity firstPlayerStakeEntity = createPlayerStakeEntity(PLAYER_STAKE_ID_1, new BigDecimal(40));
        PlayerStakeEntity secondPlayerStakeEntity = createPlayerStakeEntity(PLAYER_STAKE_ID_2, new BigDecimal(40));
        given(playerStakeRepository.findAllByAccountAndHoursThreshold(ACCOUNT_ID, ONE_HOUR))
                .willReturn(Set.of(firstPlayerStakeEntity, secondPlayerStakeEntity));
        given(playerStakeThresholdProperties.getAmount()).willReturn(AMOUNT_THRESHOLD);
        given(playerStakeThresholdProperties.getHours()).willReturn(ONE_HOUR);

        // WHEN
        stakeVerificationService.verify(ACCOUNT_ID);

        // THEN
        then(playerStakeAlertProducer).shouldHaveNoInteractions();
    }

    @Test
    void shouldSendAlertWhenPlayerStakeIsUnder100() {
        // GIVEN
        PlayerStakeEntity firstPlayerStakeEntity = createPlayerStakeEntity(PLAYER_STAKE_ID_1, new BigDecimal(40));
        PlayerStakeEntity secondPlayerStakeEntity = createPlayerStakeEntity(PLAYER_STAKE_ID_2, new BigDecimal(40));
        PlayerStakeEntity thirdPlayerStakeEntity = createPlayerStakeEntity(PLAYER_STAKE_ID_3, new BigDecimal(10));
        PlayerStakeEntity fourthPlayerStakeEntity = createPlayerStakeEntity(PLAYER_STAKE_ID_4, new BigDecimal(40));
        Set<PlayerStakeEntity> stakes = Set.of(
                firstPlayerStakeEntity,
                secondPlayerStakeEntity,
                thirdPlayerStakeEntity,
                fourthPlayerStakeEntity);
        given(playerStakeRepository.findAllByAccountAndHoursThreshold(ACCOUNT_ID, ONE_HOUR)).willReturn(stakes);
        given(playerStakeThresholdProperties.getAmount()).willReturn(AMOUNT_THRESHOLD);
        given(playerStakeThresholdProperties.getHours()).willReturn(ONE_HOUR);

        // WHEN
        stakeVerificationService.verify(ACCOUNT_ID);

        // THEN
        then(playerStakeAlertProducer).should().sendMessage(new PlayerStakeAlertMessage(ACCOUNT_ID, new BigDecimal(130)));
    }

    private PlayerStakeEntity createPlayerStakeEntity(UUID playerStakeId, BigDecimal stake) {
        return PlayerStakeEntity.builder()
                .id(playerStakeId)
                .accountId(ACCOUNT_ID)
                .stake(stake)
                .creationDateTime(LocalDateTime.now())
                .build();
    }

}