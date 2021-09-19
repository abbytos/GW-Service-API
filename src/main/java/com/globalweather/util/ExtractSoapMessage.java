package com.globalweather.util;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.io.StringReader;

@Component
public class ExtractSoapMessage {

    @Value("${SOAP_WEB_SERVER}")
    private String webserviceX;

    public StringReader cleanSoapGetCitiesMessage(SOAPMessage soapResponse) throws SOAPException {
        return new StringReader(soapResponse
                .getSOAPBody()
                .getElementsByTagNameNS(webserviceX,"GetCitiesByCountryResponse")
                .item(0)
                .getTextContent()
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">"));
    }

    public StringReader cleanSoapGetWeatherMessage(SOAPMessage soapResponse) throws SOAPException, IOException {
        StringReader reader =
                new StringReader(soapResponse.getSOAPBody()
                        .getElementsByTagNameNS(webserviceX,"GetWeatherResponse")
                        .item(0).getTextContent()
                        .replaceAll("&lt;", "<")
                        .replaceAll("&gt;", ">"));

        String stringResponse = IOUtils.toString(reader);
        if (stringResponse.startsWith("<![CDATA[")) {
            stringResponse = stringResponse.substring(9);
            int i = stringResponse.indexOf("]]>");
            if (i == -1) {
                i = stringResponse.length()-1;
            }
            stringResponse = stringResponse.substring(0, i);
        }
        return new StringReader(stringResponse);
    }
}
