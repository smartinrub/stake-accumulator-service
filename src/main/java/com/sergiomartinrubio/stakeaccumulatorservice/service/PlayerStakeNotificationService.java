package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.configuration.PlayerStakeThresholdProperties;
import com.sergiomartinrubio.stakeaccumulatorservice.messaging.PlayerStakeAlertProducer;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlertEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        BigDecimal stakeTotalAmount = playerStakeRepository
                .getTotalStakeByAccountAndTimeWindowThreshold(accountId, playerStakeThresholdProperties.getTimeWindowInMinutes())
                .orElse(BigDecimal.ZERO);


        if (stakeTotalAmount.compareTo(playerStakeThresholdProperties.getAmount()) > 0) {
            PlayerStakeAlertMessage message = new PlayerStakeAlertMessage(accountId, stakeTotalAmount);
            playerStakeAlertProducer.sendMessage(message);
            PlayerStakeAlertEntity stakeAlertEntity = new PlayerStakeAlertEntity(UUID.randomUUID(),
                    accountId, stakeTotalAmount, LocalDateTime.now());
            playerStakeAlertRepository.save(stakeAlertEntity);
        }
    }

}
