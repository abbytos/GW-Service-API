package com.globalweather.swagger;

import com.globalweather.handler.GlobalWeatherHandler;
import com.globalweather.model.GetCityByCountryResponse;
import com.globalweather.model.GetWeatherResponse;
import com.globalweather.router.GlobalWeatherRouter;
import com.globalweather.service.GlobalWeatherService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {GlobalWeatherHandler.class, GlobalWeatherRouter.class})
@WebFluxTest
public class GlobalWeatherControllerTest {

    private final String country = "Australia";
    private final String  city = "`Melbourne`";
    private final String otherCity = "Sydney";

    @Autowired
    private ApplicationContext context;
    @MockBean
    private GlobalWeatherService serviceMock;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Configuration
    @Import(GlobalWeatherService.class)
    static class Config {
    }

    @Test
    @DisplayName("Should get cities response")
    public void should_getCities_transforms_data() throws JAXBException, SOAPException, IOException {

        GetCityByCountryResponse response1 = new GetCityByCountryResponse();
        response1.setCity(city);
        response1.setCountry(country);

        GetCityByCountryResponse response2 = new GetCityByCountryResponse();
        response1.setCity(otherCity);
        response1.setCountry(country);

        Flux<GetCityByCountryResponse> fluxResponse = Flux.just(response1, response2);

        doReturn(fluxResponse).when(serviceMock).getCityByCountry(country);

        webTestClient.get()
                .uri("/getCities/Australia")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GetCityByCountryResponse.class)
                .value(response ->
                        {
                            Assertions.assertThat(response.get(0).getCountry().equalsIgnoreCase(country));
                            Assertions.assertThat(response.get(0).getCity().equalsIgnoreCase(city));
                            Assertions.assertThat(response.get(0).getCountry().equalsIgnoreCase(country));
                            Assertions.assertThat(response.get(0).getCity().equalsIgnoreCase(otherCity));
                        }
                );
    }


    @Test
    @DisplayName("Should get weather response")
    public void should_getWeather_response() throws JAXBException, SOAPException, IOException {

        GetWeatherResponse response1 = new GetWeatherResponse();
        response1.setLocation("Melbourne");
        response1.setTime("11 AM");
        response1.setWind("15 km per hour");
        response1.setVisibility("10 km");
        response1.setSkyConditions("sunny");
        response1.setTemperature("18");
        response1.setDewPoint("2 C");
        response1.setRelativeHumidity("35");
        response1.setStatus("Normal");

        Mono<GetWeatherResponse> monoResponse = Mono.just(response1);

        when(serviceMock.getWeatherOfCity(country, city)).thenReturn(monoResponse);

        webTestClient.get()
                .uri("/getWeather/Australia/Melbourne")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GetWeatherResponse.class)
                .value(response ->
                        {
                            Assertions.assertThat(response.get(0).getLocation().equalsIgnoreCase(city));
                            Assertions.assertThat(response.get(0).getTime().equalsIgnoreCase("11 AM"));
                        }
                );
    }

    @Test
    @DisplayName("Should get weather response")
    public void should_not_be_empty_getWeather_response() throws JAXBException, SOAPException, IOException {

        GetCityByCountryResponse response1 = new GetCityByCountryResponse();
        GetCityByCountryResponse response2 = new GetCityByCountryResponse();

        Flux<GetCityByCountryResponse> fluxResponse = Flux.just(response1, response2);

        doReturn(fluxResponse).when(serviceMock).getCityByCountry(country);
        webTestClient.get()
                .uri("/getCities/Australia")
                .exchange()
                .expectBodyList(GetCityByCountryResponse.class)
                .consumeWith(result -> {
                    List<GetCityByCountryResponse> responseBody = result.getResponseBody();
                    Assertions.assertThat(responseBody).isNotEmpty();
                });
    }
    @Test
    @DisplayName("Should get weather response")
    public void should_is_empty_getWeather_response() throws JAXBException, SOAPException, IOException {

        GetCityByCountryResponse response1 = new GetCityByCountryResponse();
        GetCityByCountryResponse response2 = new GetCityByCountryResponse();

        Flux<GetCityByCountryResponse> fluxResponse = Flux.just(response1, response2);

        doReturn(fluxResponse).when(serviceMock).getCityByCountry(country);
        webTestClient.get()
                .uri("/getCities/Norway")
                .exchange()
                .expectBodyList(GetCityByCountryResponse.class)
                .consumeWith(result -> {
                    List<GetCityByCountryResponse> responseBody = result.getResponseBody();
                    Assertions.assertThat(responseBody).isNotEmpty();
                });
    }
}
