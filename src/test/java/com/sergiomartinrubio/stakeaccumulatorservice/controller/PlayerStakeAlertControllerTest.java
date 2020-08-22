package com.sergiomartinrubio.stakeaccumulatorservice.controller;

import com.sergiomartinrubio.stakeaccumulatorservice.service.PlayerStakeAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerStakeAlertController.class)
class PlayerStakeAlertControllerTest {

    private static final Long ACCOUNT_ID = 123L;

    @MockBean
    private PlayerStakeAlertService playerStakeAlertService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPlayerStakeAlerts() throws Exception {
        // WHEN
        // THEN
        mockMvc.perform(get("/alert/{accountId}", ACCOUNT_ID))
                .andDo(print())
                .andExpect(status().isOk());

        // THEN
        then(playerStakeAlertService).should().findAllByAccountId(ACCOUNT_ID);
    }
}