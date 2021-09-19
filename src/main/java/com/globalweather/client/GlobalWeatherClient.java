package com.globalweather.client;

import com.globalweather.model.GetCityByCountryResponse;
import com.globalweather.model.GetWeatherResponse;
import com.globalweather.util.GenerateSoapMessage;
import com.globalweather.util.ExtractSoapMessage;

import com.globalweather.model.GetCitiesByCountryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Component
@Slf4j
public class GlobalWeatherClient {

    @Autowired
    private GenerateSoapMessage generateSoapMessage;

    @Autowired
    private ExtractSoapMessage extractSoapMessage;

    @Value("${SOAP_ENDPOINT}")
    private String soap_endpoint;

    public List<GetCityByCountryResponse> getCities(String country) throws SOAPException, JAXBException {

        //Make a server call
        SOAPMessage soapResponse =
                initConnection().call(generateSoapMessage.getCitiesByCountryMessage(country), soap_endpoint);

        log.info("DONE: SOAP message has been received." + soap_endpoint);

        //XML Conversion
        JAXBContext jaxbContext = JAXBContext.newInstance(GetCitiesByCountryResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = extractSoapMessage.cleanSoapGetCitiesMessage(soapResponse);

        GetCitiesByCountryResponse result = (GetCitiesByCountryResponse) jaxbUnmarshaller.unmarshal(reader);
        return result.getCityList();
    }


    public GetWeatherResponse getWeather(String country, String city) throws SOAPException, JAXBException, IOException {

        //Make a server call
        SOAPMessage soapResponse = initConnection()
                .call(generateSoapMessage.getWeatherMessage(country,city), soap_endpoint);
        log.info("DONE: SOAP message has been received.");

        //XML Conversion
        JAXBContext jaxbContext = JAXBContext.newInstance(GetWeatherResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        StringReader formattedResponse = extractSoapMessage.cleanSoapGetWeatherMessage(soapResponse);

        return  (GetWeatherResponse) jaxbUnmarshaller.unmarshal(formattedResponse);
    }


    private SOAPConnection initConnection() throws SOAPException
    {
        //create SOAP connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        return soapConnection;
    }

}
