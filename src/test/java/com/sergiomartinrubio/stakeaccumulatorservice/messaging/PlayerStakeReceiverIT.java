package com.sergiomartinrubio.stakeaccumulatorservice.messaging;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeDto;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.service.PlayerStakeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.timeout;

@SpringBootTest
class PlayerStakeReceiverIT {

    private static final long ACCOUNT_ID = 123L;
    private static final BigDecimal PLAYER_STAKE = new BigDecimal(40);

    @Value("${jms.player-stake-queue.name}")
    private String queueName;

    @Autowired
    private JmsTemplate jmsTemplate;

    @MockBean
    private PlayerStakeService playerStakeService;

    @Test
    void shouldReceiveMessage() {
        // GIVEN
        PlayerStakeMessage playerStakeMessage = new PlayerStakeMessage(ACCOUNT_ID, PLAYER_STAKE);

        // WHEN
        jmsTemplate.convertAndSend(queueName, playerStakeMessage);

        // THEN
        then(playerStakeService).should(timeout(1000)).process(new PlayerStakeDto(ACCOUNT_ID, PLAYER_STAKE));
    }

}