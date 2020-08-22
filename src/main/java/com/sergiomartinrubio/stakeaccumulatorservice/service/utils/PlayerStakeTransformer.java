package com.sergiomartinrubio.stakeaccumulatorservice.service.utils;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
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
