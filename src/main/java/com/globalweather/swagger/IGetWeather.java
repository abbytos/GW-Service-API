package com.globalweather.swagger;

import com.globalweather.model.GetWeatherResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@RestController("/api/")
public interface IGetWeather {

    @ApiOperation(
            value = "GetWeather",
            nickname = "getWeather",
            notes = "Get weather report for all major cities around the world.",
            tags = {"GetWeather"}
    )
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "Successfully retrieved the response"
    )})

    @RequestMapping(
            value = {"/GetWeather"},
            method = {RequestMethod.GET}
    )

    Mono<GetWeatherResponse> getWeather(@NotNull @ApiParam(value = "",required = true)
                                        @Valid @RequestParam(value = "Country",required = true) String country,
                                        @NotNull @ApiParam(value = "",required = true)
                                        @Valid @RequestParam(value = "City",required = true) String city) throws SOAPException, JAXBException, IOException;

}