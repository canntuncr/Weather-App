package com.example.weather_app.controller;

import com.example.weather_app.model.CityResponse;
import com.example.weather_app.model.CountryResponse;
import com.example.weather_app.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {

    // application.properties dosyasından API anahtarını alma
    @Value("${api.key}")
    private String apiKey;

    // application.properties dosyasından kullanıcı adını alma
    @Value("${api.username}")
    private String apiUsername;

    // Ana sayfa endpoint'i. Ülke bilgilerini alıp "index" sayfasına döndürür.
    @GetMapping("/")
    public String getIndex(Model model) {
        // GeoNames API'den ülke bilgilerini alma URL'i
        String url = "http://api.geonames.org/countryInfoJSON?lang=tr&username=" + apiUsername;
        RestTemplate restTemplate = new RestTemplate();

        try {
            // API'den gelen yanıtı CountryResponse modeline map etme
            CountryResponse countryResponse = restTemplate.getForObject(url, CountryResponse.class);
            if (countryResponse != null) {
                // Ülkeler listesini model'e ekleme
                model.addAttribute("countries", countryResponse.getGeonames());
            }
        } catch (Exception e) {
            // Hata durumunda hata mesajını model'e ekleme
            model.addAttribute("error", e.getMessage());
        }

        return "index"; // index.html sayfasını döndürme
    }

    // Şehir bilgilerini döndüren API endpoint'i
    @ResponseBody
    @GetMapping("/api/cities")
    public CityResponse getCities(@RequestParam("country") String geonameId) {
        // GeoNames API'den şehir bilgilerini alma URL'i
        String url = "http://api.geonames.org/childrenJSON?geonameId=" + geonameId + "&username=" + apiUsername;
        RestTemplate restTemplate = new RestTemplate();

        CityResponse cityResponse = null;
        try {
            // API'den gelen yanıtı CityResponse modeline map etme
            cityResponse = restTemplate.getForObject(url, CityResponse.class);
        } catch (Exception e) {
            // Hata durumunda istisnayı yazdırma
            e.printStackTrace();
        }

        return cityResponse; // JSON formatında şehir bilgilerini döndürme
    }

    // Hava durumu bilgisi sağlayan endpoint
    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model) {
        // OpenWeatherMap API'den hava durumu bilgilerini alma URL'i
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric&lang=tr";
        RestTemplate restTemplate = new RestTemplate();

        try {
            // API'den gelen yanıtı WeatherResponse modeline map etme
            WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

            if (weatherResponse != null) {
                // Hava durumu bilgilerini model'e ekleme
                model.addAttribute("city", weatherResponse.getName());
                model.addAttribute("country", weatherResponse.getSys().getCountry());
                model.addAttribute("weatherDescription", weatherResponse.getWeather().get(0).getDescription());
                model.addAttribute("temperature", weatherResponse.getMain().getTemp());
                model.addAttribute("humidity", weatherResponse.getMain().getHumidity());
                model.addAttribute("windSpeed", weatherResponse.getWind().getSpeed());
                model.addAttribute("weatherIcon", "wi wi-owm-" + weatherResponse.getWeather().get(0).getId());
            }
        } catch (Exception e) {
            // Hata durumunda kullanıcıya hata mesajı döndürme
            model.addAttribute("HATA", "Geçersiz şehir adı veya sunucu hatası. Lütfen tekrar deneyin.");
        }

        return "weather"; // weather.html sayfasını döndürme
    }
}
