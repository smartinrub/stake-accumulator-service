package com.sergiomartinrubio.stakeaccumulatorservice.acceptancetests;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@CucumberContextConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class CucumberSpringContextConfiguration {

}
