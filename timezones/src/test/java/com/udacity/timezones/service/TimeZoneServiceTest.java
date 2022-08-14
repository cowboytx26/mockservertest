package com.udacity.timezones.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static java.nio.file.Paths.get;

@ExtendWith(MockitoExtension.class)
public class TimeZoneServiceTest {

    static WireMockServer wireMock = new WireMockServer(wireMockConfig().port(8089));
    private static String serverPath = "http://localhost:8089";
    private static String TimezoneAPIPath = "/api/timezone/";
    private static String Area = "europe";

    private TimeZoneService timeZoneService;

    @BeforeAll
    static void setup() {
        wireMock.start();
    }

    @AfterAll
    static void cleanup() {
        wireMock.stop();
    }

    @BeforeEach
    void init() {
        wireMock.resetAll();
        timeZoneService = new TimeZoneService(serverPath);
    }

    @Test
    public void getAvailableTimezoneText(){
        String expectedReturn = "Amsterdam, Andorra, Astrakhan, Athens";
        wireMock.stubFor(get(urlEqualTo(TimezoneAPIPath + Area + "/"))
                .willReturn(aResponse().withStatus(200).withBody(expectedReturn)));
        String timeZoneText = timeZoneService.getAvailableTimezoneText(Area);
        System.out.println(timeZoneService.getAvailableTimezoneText(Area));
        assertTrue(timeZoneText.contains(expectedReturn));
    }
}
