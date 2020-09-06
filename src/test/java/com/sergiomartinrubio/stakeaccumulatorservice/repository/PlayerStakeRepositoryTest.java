package com.sergiomartinrubio.stakeaccumulatorservice.repository;

import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class PlayerStakeRepositoryTest {

    private static final Long ACCOUNT_ID = 123L;
    private static final int MINUTES = 60;
    private static final LocalDateTime CREATION_DATE_TIME_LESS_THAN_AN_HOUR = LocalDateTime.now()
            .minus(59, ChronoUnit.MINUTES);
    private static final LocalDateTime CREATION_DATE_TIME_MORE_THAN_AN_HOUR = LocalDateTime.now()
            .minus(61, ChronoUnit.MINUTES);
    private static final UUID STAKE_ID_1 = UUID.randomUUID();
    private static final UUID STAKE_ID_2 = UUID.randomUUID();

    @Autowired
    private PlayerStakeRepository playerStakeRepository;

    @Test
    void shouldReturnTotalStakeAmountWithinThreshold() {
        // GIVEN
        PlayerStakeEntity firstPlayerStake = createPlayerStake(STAKE_ID_1, CREATION_DATE_TIME_LESS_THAN_AN_HOUR);
        PlayerStakeEntity secondPlayerStake = createPlayerStake(STAKE_ID_2, CREATION_DATE_TIME_MORE_THAN_AN_HOUR);
        playerStakeRepository.save(firstPlayerStake);
        playerStakeRepository.save(secondPlayerStake);

        // WHEN
        Optional<BigDecimal> stakeTotalAmount = playerStakeRepository.getTotalStakeByAccountAndTimeWindowThreshold(ACCOUNT_ID, MINUTES);

        // THEN
        assertThat(stakeTotalAmount).isNotEmpty();
        assertThat(stakeTotalAmount.get()).isEqualByComparingTo(BigDecimal.valueOf(40));
    }

    @Test
    void shouldReturnNullWhenNoStakeWithinAnHour() {
        // GIVEN
        PlayerStakeEntity secondPlayerStake = createPlayerStake(STAKE_ID_2, CREATION_DATE_TIME_MORE_THAN_AN_HOUR);
        playerStakeRepository.save(secondPlayerStake);

        // WHEN
        Optional<BigDecimal> stakeTotalAmount = playerStakeRepository.getTotalStakeByAccountAndTimeWindowThreshold(ACCOUNT_ID, MINUTES);

        // THEN
        assertThat(stakeTotalAmount).isEmpty();
    }

    private PlayerStakeEntity createPlayerStake(UUID stakeId, LocalDateTime creationDateTime) {
        return PlayerStakeEntity.builder()
                .id(stakeId)
                .accountId(ACCOUNT_ID)
                .stake(new BigDecimal(40))
                .creationDateTime(creationDateTime)
                .build();
    }
}