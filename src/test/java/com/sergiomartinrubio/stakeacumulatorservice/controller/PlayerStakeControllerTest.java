package com.sergiomartinrubio.stakeacumulatorservice.controller;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.service.PlayerStakeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerStakeController.class)
class PlayerStakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerStakeService playerStakeService;

    @Test
    public void shouldProcessPlayerStake() throws Exception {
        // GIVEN
        String requestBody = "{\"accountId\":123, \"stake\":40}";

        // WHEN
        // THEN
        mockMvc.perform(post("/player-stake")
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
        then(playerStakeService).should().process(new PlayerStake(123, new BigDecimal(40)));
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidInput() throws Exception {
        // GIVEN
        String requestBody = "{\"accountId\":123}";

        // WHEN
        // THEN
        mockMvc.perform(post("/player-stake")
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
        then(playerStakeService).shouldHaveNoInteractions();
    }
}