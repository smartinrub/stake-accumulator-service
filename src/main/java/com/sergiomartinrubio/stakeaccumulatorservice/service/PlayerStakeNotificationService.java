package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.configuration.PlayerStakeThresholdProperties;
import com.sergiomartinrubio.stakeaccumulatorservice.messaging.PlayerStakeAlertProducer;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlertEntity;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(PlayerStakeThresholdProperties.class)
public class PlayerStakeNotificationService {

    private final PlayerStakeRepository playerStakeRepository;
    private final PlayerStakeAlertProducer playerStakeAlertProducer;
    private final PlayerStakeThresholdProperties playerStakeThresholdProperties;
    private final PlayerStakeAlertRepository playerStakeAlertRepository;

    public void evaluate(Long accountId) {
        Set<PlayerStakeEntity> playerStakes = playerStakeRepository
                .findAllByAccountAndHoursThreshold(accountId, playerStakeThresholdProperties.getHours());

        BigDecimal totalStake = getTotalStake(playerStakes);

        if (totalStake.compareTo(playerStakeThresholdProperties.getAmount()) > 0) {
            PlayerStakeAlertMessage message = new PlayerStakeAlertMessage(accountId, totalStake);
            playerStakeAlertProducer.sendMessage(message);
            PlayerStakeAlertEntity stakeAlertEntity = new PlayerStakeAlertEntity(UUID.randomUUID(),
                    accountId, totalStake, LocalDateTime.now());
            playerStakeAlertRepository.save(stakeAlertEntity);
        }
    }

    private BigDecimal getTotalStake(Set<PlayerStakeEntity> playerStakes) {
        return playerStakes.stream()
                .map(PlayerStakeEntity::getStake)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
