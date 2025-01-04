package com.example.weather_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityResponse {

    public List<City> geonames;

    @Getter
    @Setter
    public static class City{
        public Integer geonameId; //ülkenin idsi
        public String toponymName; //şehrin ismi
        public String countryId; // ait olduğu ülkenin idsi
        public String countryCode; // ait olduğu ülkenin iki harf kodu
    }

}
