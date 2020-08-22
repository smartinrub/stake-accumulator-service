package com.sergiomartinrubio.stakeaccumulatorservice.messaging;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerStakeAlertProducer {

    @Value("${jms.player-stake-alert-queue.name}")
    private String queueName;

    private final JmsTemplate jmsTemplate;

    public void sendMessage(PlayerStakeAlertMessage message) {
        log.info("Sending alert to account {} with cumulated stake {}", message.getAccountId(), message.getCumulatedAmount());
        jmsTemplate.convertAndSend(queueName, message);
    }

}
