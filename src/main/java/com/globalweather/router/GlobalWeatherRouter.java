package com.globalweather.router;

import com.globalweather.handler.GlobalWeatherHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class GlobalWeatherRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GlobalWeatherHandler handler){
        return RouterFunctions
                .route(RequestPredicates.GET("/getWeather/{country}/{city}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::getWeather)

                .andRoute(RequestPredicates.GET("/getCities/{country}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::getCitiesByCountry);
    }
}
