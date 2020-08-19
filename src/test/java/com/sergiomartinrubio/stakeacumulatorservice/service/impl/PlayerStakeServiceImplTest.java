package com.sergiomartinrubio.stakeacumulatorservice.service.impl;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import com.sergiomartinrubio.stakeacumulatorservice.service.PlayerStakeVerificationService;
import com.sergiomartinrubio.stakeacumulatorservice.service.utils.PlayerStakeTransformerImpl;
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
        given(playerStakeTransformer.transformPlayerStake(playerStake)).willReturn(playerStakeEntity);

        // WHEN
        playerStakeService.process(playerStake);

        // THEN
        then(playerStakeRepository).should().save(playerStakeEntity);
        then(playerStakeVerificationService).should().evaluate(ACCOUNT_ID);
    }

}
