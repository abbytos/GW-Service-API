package com.globalweather.service;

import com.globalweather.model.GetWeatherResponse;
import com.globalweather.model.GetCityByCountryResponse;
import com.globalweather.client.GlobalWeatherClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.List;

@Service
public class GlobalWeatherService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private GlobalWeatherClient globalWeatherClient;

    public GlobalWeatherService(ApplicationEventPublisher applicationEventPublisher, GlobalWeatherClient globalWeatherClient) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.globalWeatherClient = globalWeatherClient;
    }

    public Flux<List<GetCityByCountryResponse>> getCityByCountry(String country) throws SOAPException, IOException, JAXBException {
        return Flux.just(globalWeatherClient.getCities(country));
    }

    public Mono<GetWeatherResponse> getWeatherOfCity(String country, String city) throws JAXBException, SOAPException, IOException {
        return Mono.just(globalWeatherClient.getWeather(country, city));
    }
}
