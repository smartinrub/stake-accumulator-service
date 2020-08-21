package com.sergiomartinrubio.stakeacumulatorservice.messaging;

import com.sergiomartinrubio.stakeacumulatorservice.model.AlertPlayerStakeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerStakeAlertProducer {

    @Value("${jms.player-stake-alert-queue.name}")
    private String queueName;

    private final JmsTemplate jmsTemplate;

    public void sendMessage(AlertPlayerStakeMessage alertPlayerStakeMessage) {
        jmsTemplate.convertAndSend(queueName, alertPlayerStakeMessage);
    }

}
