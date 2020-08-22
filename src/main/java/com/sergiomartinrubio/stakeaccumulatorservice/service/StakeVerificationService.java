package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.configuration.PlayerStakeThresholdProperties;
import com.sergiomartinrubio.stakeaccumulatorservice.messaging.PlayerStakeAlertProducer;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(PlayerStakeThresholdProperties.class)
public class StakeVerificationService {

    private final PlayerStakeRepository playerStakeRepository;
    private final PlayerStakeAlertProducer playerStakeAlertProducer;
    private final PlayerStakeThresholdProperties playerStakeThresholdProperties;

    public void verify(Long accountId) {
        Set<PlayerStakeEntity> playerStakes = playerStakeRepository
                .findAllByAccountAndHoursThreshold(accountId, playerStakeThresholdProperties.getHours());

        BigDecimal totalStake = getTotalStake(playerStakes);

        if (totalStake.compareTo(playerStakeThresholdProperties.getAmount()) > 0) {
            playerStakeAlertProducer.sendMessage(new PlayerStakeAlertMessage(accountId, totalStake));
        }
    }

    private BigDecimal getTotalStake(Set<PlayerStakeEntity> playerStakes) {
        return playerStakes.stream()
                .map(PlayerStakeEntity::getStake)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
