package com.sergiomartinrubio.stakeacumulatorservice.service.utils;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PlayerStakeTransformer {

    private final Clock clock;

    public PlayerStakeEntity transformPlayerStake(PlayerStake playerStake) {
        return PlayerStakeEntity.builder()
                .id(UUID.randomUUID())
                .accountId(playerStake.getAccountId())
                .stake(playerStake.getStake())
                .creationDateTime(LocalDateTime.ofInstant(Instant.now(clock), clock.getZone()))
                .build();
    }
}
