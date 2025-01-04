package com.example.weather_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResponse {


    private List<Country> geonames;

    @Setter
    @Getter
    public static class Country {
        private String countryName;  // görüntelenecek isim
        private String countryCode; // ülkenin iki harf kodu
        private Integer geonameId; // ülkenin geoname idsi
    }

}
