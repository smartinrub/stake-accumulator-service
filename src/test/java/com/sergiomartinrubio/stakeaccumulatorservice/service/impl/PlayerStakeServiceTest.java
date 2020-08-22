package com.sergiomartinrubio.stakeaccumulatorservice.service.impl;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
import com.sergiomartinrubio.stakeaccumulatorservice.service.PlayerStakeService;
import com.sergiomartinrubio.stakeaccumulatorservice.service.StakeVerificationService;
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
    private StakeVerificationService stakeVerificationService;

    @InjectMocks
    private PlayerStakeService playerStakeService;

    @Test
    void shouldSavePlayerStake() {
        // GIVEN
        PlayerStake playerStake = new PlayerStake(ACCOUNT_ID, new BigDecimal(40));
        PlayerStakeEntity playerStakeEntity = PlayerStakeEntity.builder()
                .accountId(playerStake.getAccountId())
                .stake(playerStake.getStake())
                .creationDateTime(NOW)
                .build();
        given(playerStakeTransformer.transformPlayerStake(playerStake)).willReturn(playerStakeEntity);

        // WHEN
        playerStakeService.process(playerStake);

        // THEN
        then(playerStakeRepository).should().save(playerStakeEntity);
        then(stakeVerificationService).should().verify(ACCOUNT_ID);
    }

}
