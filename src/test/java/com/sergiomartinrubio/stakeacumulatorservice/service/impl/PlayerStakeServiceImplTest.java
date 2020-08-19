package com.sergiomartinrubio.stakeacumulatorservice.service.impl;

import com.sergiomartinrubio.stakeacumulatorservice.service.utils.PlayerStakeTransformerImpl;
import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import com.sergiomartinrubio.stakeacumulatorservice.service.PlayerStakeVerificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerStakeServiceImplTest {

    private static final Long ACCOUNT_ID = 123L;
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Mock
    private PlayerStakeTransformerImpl playerStakeTransformer;

    @Mock
    private PlayerStakeRepository playerStakeRepository;

    @Mock
    private PlayerStakeVerificationService playerStakeVerificationService;

    @InjectMocks
    private PlayerStakeServiceImpl playerStakeService;

    @Test
    void shouldSavePlayerStake() {
        // GIVEN
        PlayerStake playerStake = new PlayerStake(ACCOUNT_ID, new BigDecimal(40));
        PlayerStakeEntity playerStakeEntity = PlayerStakeEntity.builder()
                .accountId(playerStake.getAccountId())
                .stake(playerStake.getStake())
                .creationDateTime(NOW)
                .build();
        when(playerStakeTransformer.transformPlayerStake(playerStake)).thenReturn(playerStakeEntity);

        // WHEN
        playerStakeService.process(playerStake);

        // THEN
        verify(playerStakeRepository).save(playerStakeEntity);
        verify(playerStakeVerificationService).evaluate(ACCOUNT_ID);
    }

}
