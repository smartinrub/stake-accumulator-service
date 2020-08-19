package com.sergiomartinrubio.stakeacumulatorservice;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import com.sergiomartinrubio.stakeacumulatorservice.service.utils.PlayerStakeTransformerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerStakeTransformerImplTest {

    private static final int ACCOUNT_ID = 123;
    public static final Instant INSTANT_NOW = Instant.now();
    public static final ZoneId UK_ZONE_ID = ZoneId.of("UTC");
    private static final LocalDateTime NOW = LocalDateTime.ofInstant(INSTANT_NOW, UK_ZONE_ID);

    @Mock
    private Clock clock;

    @InjectMocks
    private PlayerStakeTransformerImpl playerStakeTransformer;

    @Test
    void shouldTransformPlayerStakeObject() {
        // GIVEN
        PlayerStake playerStake = new PlayerStake(ACCOUNT_ID, new BigDecimal(40));
        PlayerStakeEntity playerStakeEntity = PlayerStakeEntity.builder()
                .accountId(playerStake.getAccountId())
                .stake(playerStake.getStake())
                .creationDateTime(NOW)
                .build();
        when(clock.instant()).thenReturn(INSTANT_NOW);
        when(clock.getZone()).thenReturn(UK_ZONE_ID);

        // WHEN
        PlayerStakeEntity result = playerStakeTransformer.transformPlayerStake(playerStake);

        // THEN
        assertThat(result).isEqualTo(playerStakeEntity);
    }

}