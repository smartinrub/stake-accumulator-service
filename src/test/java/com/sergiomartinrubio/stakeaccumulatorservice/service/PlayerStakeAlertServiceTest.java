package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlert;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlayerStakeAlertServiceTest {

    private static final UUID PLAYER_STAKE_ALERT_ID = UUID.randomUUID();
    private static final Long ACCOUNT_ID = 123L;
    private static final LocalDateTime CREATION_DATE_TIME = LocalDateTime.now();

    @Mock
    private PlayerStakeAlertRepository playerStakeAlertRepository;

    @InjectMocks
    private PlayerStakeAlertService playerStakeAlertService;

    @Test
    void shouldReturnPlayerStakeAlerts() {
        // GIVEN
        PlayerStakeAlertEntity playerStakeAlert = PlayerStakeAlertEntity.builder()
                .id(PLAYER_STAKE_ALERT_ID)
                .accountId(ACCOUNT_ID)
                .cumulatedAmount(new BigDecimal(120))
                .creationDateTime(CREATION_DATE_TIME)
                .build();
        given(playerStakeAlertRepository.findAllByAccountIdEquals(ACCOUNT_ID)).willReturn(Set.of(playerStakeAlert));

        // WHEN
        Set<PlayerStakeAlert> playerStakeAlerts = playerStakeAlertService.findAllByAccountId(ACCOUNT_ID);

        // THEN
        PlayerStakeAlert expected = PlayerStakeAlert.builder()
                .id(PLAYER_STAKE_ALERT_ID)
                .accountId(ACCOUNT_ID)
                .cumulatedAmount(new BigDecimal(120))
                .creationDateTime(CREATION_DATE_TIME)
                .build();
        assertThat(playerStakeAlerts).containsExactlyInAnyOrder(expected);
    }

}