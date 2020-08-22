package com.sergiomartinrubio.stakeacumulatorservice.messaging;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStakeMessage;
import com.sergiomartinrubio.stakeacumulatorservice.service.PlayerStakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerStakeReceiver {

    private final PlayerStakeService playerStakeService;

    @JmsListener(destination = "${jms.player-stake-queue.name}")
    public void receiveMessage(PlayerStakeMessage message) {
        log.info("Message received for account {} with stake {}", message.getAccountId(), message.getStake());
        playerStakeService.process(new PlayerStake(message.getAccountId(), message.getStake()));
    }
}
