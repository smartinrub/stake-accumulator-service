package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeDto;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStake;
import com.sergiomartinrubio.stakeaccumulatorservice.service.utils.PlayerStakeTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PlayerStakeServiceTest {

    private static final Long ACCOUNT_ID = 123L;
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Mock
    private PlayerStakeTransformer playerStakeTransformer;

    @Mock
    private PlayerStakeRepository playerStakeRepository;

    @Mock
    private PlayerStakeNotificationService playerStakeNotificationService;

    @InjectMocks
    private PlayerStakeService playerStakeService;

    @Test
    void shouldSavePlayerStake() {
        // GIVEN
        PlayerStakeDto playerStake = new PlayerStakeDto(ACCOUNT_ID, new BigDecimal(40));
        PlayerStake playerStakeEntity = PlayerStake.builder()
                .accountId(playerStake.getAccountId())
                .stake(playerStake.getStake())
                .creationDateTime(NOW)
                .build();
        given(playerStakeTransformer.transform(playerStake)).willReturn(playerStakeEntity);

        // WHEN
        playerStakeService.process(playerStake);

        // THEN
        then(playerStakeRepository).should().save(playerStakeEntity);
        then(playerStakeNotificationService).should().evaluate(ACCOUNT_ID);
    }

}
