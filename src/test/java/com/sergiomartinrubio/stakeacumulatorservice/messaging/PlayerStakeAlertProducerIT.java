package com.sergiomartinrubio.stakeacumulatorservice.messaging;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStakeAlertMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlayerStakeAlertProducerIT {

    private static final long ACCOUNT_ID = 123L;
    private static final BigDecimal CUMULATED_AMOUNT = new BigDecimal(130);

    @Value("${jms.player-stake-alert-queue.name}")
    private String queueName;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private PlayerStakeAlertProducer playerStakeAlertProducer;

    @Test
    void shouldSendMessage() {
        // GIVEN
        PlayerStakeAlertMessage message = new PlayerStakeAlertMessage(ACCOUNT_ID, CUMULATED_AMOUNT);

        // WHEN
        playerStakeAlertProducer.sendMessage(message);

        // THEN
        PlayerStakeAlertMessage result = (PlayerStakeAlertMessage) jmsTemplate.receiveAndConvert(queueName);
        assertThat(result).isEqualTo(message);
    }

}