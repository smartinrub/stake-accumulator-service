package com.sergiomartinrubio.stakeacumulatorservice.messaging;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStakeMessage;
import com.sergiomartinrubio.stakeacumulatorservice.service.impl.PlayerStakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerStakeReceiver {

    private final PlayerStakeService playerStakeService;

    @JmsListener(destination = "${jms.player-stake-queue.name}")
    public void receiveMessage(PlayerStakeMessage message) {
        playerStakeService.process(new PlayerStake(message.getAccountId(), message.getStake()));
    }
}
