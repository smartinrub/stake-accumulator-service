package com.sergiomartinrubio.stakeaccumulatorservice.messaging;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.service.PlayerStakeService;
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
