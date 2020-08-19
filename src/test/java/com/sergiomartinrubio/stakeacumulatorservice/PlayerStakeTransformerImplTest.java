package com.sergiomartinrubio.stakeacumulatorservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerStakeTransformerImplTest {

    public static final Instant NOW = Instant.now();
    public static final ZoneId UK_ZONE_ID = ZoneId.of("UK");

    @Mock
    private Clock clock;

    @Test
    void test() {
        when(clock.instant()).thenReturn(NOW);
        when(clock.getZone()).thenReturn(UK_ZONE_ID);
    }

}