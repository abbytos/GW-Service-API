package com.globalweather.handler;

import com.globalweather.model.GetCityByCountryResponse;
import com.globalweather.model.GetWeatherResponse;
import com.globalweather.service.GlobalWeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.regex.Pattern;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Slf4j
@Component
public class GlobalWeatherHandler {

    @Autowired
    private GlobalWeatherService weatherAppService;
    private Pattern inputPattern = Pattern.compile("^[ A-Za-z]+$");

    public Mono<ServerResponse> getCitiesByCountry(ServerRequest serverRequest) {
        String country = serverRequest.pathVariable("country").trim();

        if(!inputPattern.matcher(country).matches()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a valid value.");
        }

        try {
            return ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(weatherAppService.getCityByCountry(country), GetCityByCountryResponse.class);
        } catch (SOAPException | IOException | JAXBException e) {
            log.error("Exception occurred while making SOAP call or reading SOAP response" + e.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exception occurred while making SOAP call or reading SOAP response"
            );
        }
    }


    public Mono<ServerResponse> getWeather(ServerRequest serverRequest) {

        String country = serverRequest.pathVariable("country");
        String city = serverRequest.pathVariable("city");

        if(!inputPattern.matcher(country).matches() || !inputPattern.matcher(city).matches()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a valid parameter!"
            );
        }
        try {
            return ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(weatherAppService.getWeatherOfCity(country, city), GetWeatherResponse.class);
        } catch (SOAPException | JAXBException | IOException e) {
            log.error("Exception occurred while making SOAP call or reading SOAP response" + e.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exception occurred while making SOAP call or reading SOAP response");
        }
    }

}
