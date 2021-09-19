package com.globalweather.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.soap.*;

@Component
public class GenerateSoapMessage{

    @Value("${SOAP_ENDPOINT}")
    private String soap_endpoint;

    @Value("${SOAP_WEB_SERVER}")
    private String webserviceX ;

    public SOAPMessage getCitiesByCountryMessage(String country) throws SOAPException {
        SOAPMessage soapMessage = this.createMessage();
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        SOAPBodyElement element = soapMessage.getSOAPBody().addBodyElement(soapEnvelope.createName("GetCitiesByCountry", "web",webserviceX));
        element.addChildElement(soapEnvelope.createName("CountryName", "web", webserviceX)).addTextNode(country);
        soapMessage.saveChanges();
        return soapMessage;
    }

    public SOAPMessage getWeatherMessage(String country, String city) throws SOAPException {
        SOAPMessage soapMessage = this.createMessage();
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        SOAPBodyElement element = soapMessage.getSOAPBody().addBodyElement(soapEnvelope.createName("GetWeather", "web",webserviceX));
        element.addChildElement(soapEnvelope.createName("CountryName", "web", webserviceX)).addTextNode(country);
        element.addChildElement(soapEnvelope.createName("CityName", "web", webserviceX)).addTextNode(city);
        soapMessage.saveChanges();
        return soapMessage;
    }

    private SOAPMessage createMessage() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soap_endpoint);

        return soapMessage;
    }
}
