package com.sergiomartinrubio.stakeacumulatorservice.service.utils;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import com.sergiomartinrubio.stakeacumulatorservice.service.utils.impl.PlayerStakeTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PlayerStakeTransformerImpl implements PlayerStakeTransformer {

    private final Clock clock;

    @Override
    public PlayerStakeEntity transformPlayerStake(PlayerStake playerStake) {
        return PlayerStakeEntity.builder()
                .accountId(playerStake.getAccountId())
                .stake(playerStake.getStake())
                .creationDateTime(LocalDateTime.ofInstant(Instant.now(clock), clock.getZone()))
                .build();
    }
}
