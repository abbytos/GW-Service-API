package com.globalweather.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message = "Please enter a value";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions option) {
        Map<String, Object> map = super.getErrorAttributes(request, option);

        if (getError(request) instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) getError(request);
            map.put("Exception", ex.getClass().getSimpleName());
            map.put("Message", ex.getMessage());
            map.put("Status", ex.getStatus().value());
            map.put("Error", ex.getStatus().getReasonPhrase());

            return map;
        }

        map.put("Exception", "SystemException");
        map.put("Message", "System Error, Please, check the logs for more information.");
        map.put("Status", "500");
        map.put("Error", "System Error");
        return map;
    }
}