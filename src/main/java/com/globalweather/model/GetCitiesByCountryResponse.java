package com.globalweather.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="NewDataSet")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class GetCitiesByCountryResponse {
    @XmlElement(name = "Table")
    private List<GetCityByCountryResponse> cityList;

    public List<GetCityByCountryResponse> getCityList() {
        return cityList;
    }

    public void setCityList(List<GetCityByCountryResponse> cityList) {
        this.cityList = cityList;
    }
}
