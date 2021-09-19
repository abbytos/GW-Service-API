package com.globalweather;

import com.globalweather.handler.GlobalWeatherHandler;
import com.globalweather.model.GetCityByCountryResponse;
import com.globalweather.model.GetWeatherResponse;
import com.globalweather.router.GlobalWeatherRouter;
import com.globalweather.service.GlobalWeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {GlobalWeatherHandler.class, GlobalWeatherRouter.class})
@WebFluxTest
public class GlobalWeatherRouterTest {

    @Autowired
    private ApplicationContext context;
    @MockBean
    private GlobalWeatherService serviceMock;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    @DisplayName("Should return 200 OK for GetCities.")
    public void should_return_OK_for_getCities_with_parameter() throws SOAPException, JAXBException, IOException {
        GetCityByCountryResponse response1 = new GetCityByCountryResponse();
        GetCityByCountryResponse response2 = new GetCityByCountryResponse();

        List<GetCityByCountryResponse> list = Arrays.asList(response1,response2);

        Flux<GetCityByCountryResponse> fluxResponse = Flux.just(response1, response2);

        doReturn(fluxResponse).when(serviceMock).getCityByCountry("Australia");

        webTestClient.get().uri("/getCities/Australia")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Should return 200 OK for GetWeather.")
    public void should_return_OK_for_getWeather_with_parameter() throws JAXBException, SOAPException, IOException {
        GetWeatherResponse response1 = new GetWeatherResponse();
        Mono<GetWeatherResponse> monoResponse = Mono.just(response1);

        when(serviceMock.getWeatherOfCity("Australia", "Melbourne")).thenReturn(monoResponse);

        webTestClient.get().uri("/getWeather/Australia/Melbourne")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Should throw 404 Not Found for GetCities.")
    public void should_throw_not_found_for_getCities_without_parameter() throws SOAPException, JAXBException, IOException {
        GetCityByCountryResponse response1 = new GetCityByCountryResponse();
        GetCityByCountryResponse response2 = new GetCityByCountryResponse();

        List<GetCityByCountryResponse> list = Arrays.asList(response1,response2);

        Flux<GetCityByCountryResponse> fluxResponse = Flux.just(response1, response2);

        doReturn(fluxResponse).when(serviceMock).getCityByCountry("Australia");

        webTestClient.get().uri("/getCities")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Should throw 404 Not Found for GetWeather.")
    public void should_throw_not_found_for_getWeather_without_parameter() throws JAXBException, SOAPException, IOException {
        GetWeatherResponse response1 = new GetWeatherResponse();
        Mono<GetWeatherResponse> monoResponse = Mono.just(response1);

        when(serviceMock.getWeatherOfCity("Australia", "Melbourne")).thenReturn(monoResponse);

        webTestClient.get().uri("/getWeather")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }
}
