package com.globalweather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class GlobalWeatherMain  {
	public static void main(String[] args) {
		SpringApplication.run(GlobalWeatherMain.class, args);
	}
}
