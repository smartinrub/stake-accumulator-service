package com.sergiomartinrubio.stakeaccumulatorservice.acceptancetests.stepdefs;

import com.sergiomartinrubio.stakeaccumulatorservice.acceptancetests.CucumberSpringContextConfiguration;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeMessage;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlertEntity;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerStakeAccumulatorStepDefsTest extends CucumberSpringContextConfiguration {

    private static final UUID PLAYER_STAKE_ID = UUID.randomUUID();
    private static final Long ACCOUNT_ID = 123L;

    @Value("${jms.player-stake-queue.name}")
    private String playerStakeQueueName;

    @Value("${jms.player-stake-alert-queue.name}")
    private String playerStakeAlertQueueName;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private PlayerStakeRepository playerStakeRepository;

    @Autowired
    private PlayerStakeAlertRepository playerStakeAlertRepository;

    @After
    public void teardown() {
        playerStakeRepository.deleteAll();
        playerStakeAlertRepository.deleteAll();
    }

    @Given("no messages stored")
    public void no_messages_stored() {
        playerStakeRepository.deleteAll();
    }

    @Given("message stored within period of time with amount {}")
    public void messages_stored_within_period_of_time_with_amount(BigDecimal amount) {
        playerStakeRepository.save(createPlayerStakeEntity(amount, LocalDateTime.now().minus(59, MINUTES)));
    }

    @When("the message is received with amount {}")
    public void the_message_is_received_with_amount(BigDecimal amount) {
        jmsTemplate.convertAndSend(playerStakeQueueName, new PlayerStakeMessage(ACCOUNT_ID, amount));
    }

    @Then("do not receive message alert")
    public void do_not_receive_message_alert() {
        jmsTemplate.setReceiveTimeout(1000);
        PlayerStakeAlertMessage message = (PlayerStakeAlertMessage) jmsTemplate
                .receiveAndConvert(playerStakeAlertQueueName);
        assertThat(message).isNull();
    }

    @Then("receive message alert with cumulated amount {}")
    public void receive_message_alert_with_cumulated_amount(BigDecimal amount) {
        PlayerStakeAlertMessage message = (PlayerStakeAlertMessage) jmsTemplate
                .receiveAndConvert(playerStakeAlertQueueName);
        assertThat(message).isNotNull();
        assertThat(message.getCumulatedAmount()).isEqualTo(amount);
    }

    @Then("do not store alert")
    public void do_not_store_alert() {
        Set<PlayerStakeAlertEntity> alerts = playerStakeAlertRepository.findAllByAccountIdEquals(ACCOUNT_ID);
        assertThat(alerts).isEmpty();
    }

    @Then("store alert with amount {}")
    public void store_alert_with_amount(BigDecimal amount) {
        Set<PlayerStakeAlertEntity> alerts = playerStakeAlertRepository.findAllByAccountIdEquals(ACCOUNT_ID);
        assertThat(alerts).hasSize(1);
        PlayerStakeAlertEntity playerStakeAlertEntity = alerts.iterator().next();
        assertThat(playerStakeAlertEntity.getCumulatedAmount()).isEqualTo(amount);
    }


    private PlayerStakeEntity createPlayerStakeEntity(BigDecimal amount, LocalDateTime creationDateTime) {
        return PlayerStakeEntity.builder()
                .id(PLAYER_STAKE_ID)
                .accountId(ACCOUNT_ID)
                .stake(amount)
                .creationDateTime(creationDateTime)
                .build();
    }
}
