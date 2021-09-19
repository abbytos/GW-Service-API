package com.globalweather.swagger;

import com.globalweather.client.GlobalWeatherClient;
import com.globalweather.model.GetWeatherResponse;
import com.globalweather.model.GetCityByCountryResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.List;


@Controller
public class GlobalWeatherController implements IGetCitiesByCountry,IGetWeather {

    private final ApplicationEventPublisher applicationEventPublisher;
    private GlobalWeatherClient globalWeatherClient;

    public GlobalWeatherController(ApplicationEventPublisher applicationEventPublisher, GlobalWeatherClient globalWeatherClient) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.globalWeatherClient = globalWeatherClient;
    }

    @Override
    public Flux<List<GetCityByCountryResponse>> getCitiesByCountry(String country) throws SOAPException, JAXBException {
        return Flux.just(globalWeatherClient.getCities(country));
    }

    @Override
    public Mono<GetWeatherResponse> getWeather(String country, String city) throws SOAPException, JAXBException, IOException {
        return Mono.just(globalWeatherClient.getWeather(country, city));
    }
}
