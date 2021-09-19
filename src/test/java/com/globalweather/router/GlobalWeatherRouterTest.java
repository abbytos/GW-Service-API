package com.globalweather.router;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GlobalWeatherRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Should return 200 OK for GetCities.")
    public void should_return_OK_for_getCities_with_parameter(){
        webTestClient.get().
                uri("/getCities/Australia")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Should return 200 OK for GetWeather.")
    public void should_return_OK_for_getWeather_with_parameter() {
        webTestClient.get()
                .uri("/getWeather/Australia/Melbourne")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Should throw 400 BAD_REQUEST for GetCities.")
    public void should_throw_not_found_for_getCities_without_parameter(){
        webTestClient.get()
                .uri("/getCities")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Should throw 400 BAD_REQUEST for GetWeather.")
    public void should_throw_not_found_for_getWeather_without_parameter(){
        webTestClient.get()
                .uri("/getWeather")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }
}