package com.globalweather.swagger;

import com.globalweather.model.GetCityByCountryResponse;
import com.sun.istack.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.List;

@RestController("/api/")
public interface IGetCitiesByCountry
    {
        @ApiOperation(
                value = "GetCitiesByCountry",
                nickname = "getCitiesByCountry",
                notes = "Get all major cities by country name(full / part).",
                tags = {"GetCitiesByCountry"}
        )

        @ApiResponses({@ApiResponse(
                code = 200,
                message = "Successfully retrieved the response"
        )})

        @RequestMapping(
                value = {"/GetCitiesByCountry"},
                method = {RequestMethod.GET}
        )

        Flux<List<GetCityByCountryResponse>> getCitiesByCountry(@NotNull @ApiParam(value = "",required = true)
                                                                @Valid @RequestParam(value = "Country",required = true) String country) throws SOAPException, IOException, JAXBException;
    }

