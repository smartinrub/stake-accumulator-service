package com.sergiomartinrubio.stakeaccumulatorservice.service.utils;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeDto;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStake;
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

    public PlayerStake transform(PlayerStakeDto playerStake) {
        return PlayerStake.builder()
                .id(UUID.randomUUID())
                .accountId(playerStake.getAccountId())
                .stake(playerStake.getStake())
                .creationDateTime(LocalDateTime.ofInstant(Instant.now(clock), clock.getZone()))
                .build();
    }
}
