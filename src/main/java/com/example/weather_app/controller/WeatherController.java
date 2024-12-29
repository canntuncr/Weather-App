package com.example.weather_app.controller;

import com.example.weather_app.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {

    // application.properties dosyasından API anahtarını alma
    @Value("${api.key}")
    private String apiKey;

    // Ana sayfa için indexleri döndürme işlemi
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    // Hava durumu bilgisi için endpoint
    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model) {
        // OpenWeatherMap API'sine yapılacak isteğin URL'si ve gelen bilgilerin Türkçe olarak alınması
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric&lang=tr";
        RestTemplate restTemplate = new RestTemplate();

        try {
            // API'den gelen yanıtın WeatherResponse modeli sınıfına dönüştürülmesi
            WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

            // Eğer yanıt boş değilse model'e hava durumu verilerini ekle
            if (weatherResponse != null) {
                model.addAttribute("city", weatherResponse.getName()); // Şehir adı
                model.addAttribute("country", weatherResponse.getSys().getCountry()); // Ülke kodu
                model.addAttribute("weatherDescription", weatherResponse.getWeather().get(0).getDescription()); // Hava durumu açıklaması
                model.addAttribute("temperature", weatherResponse.getMain().getTemp()); // Sıcaklık
                model.addAttribute("humidity", weatherResponse.getMain().getHumidity()); // Nem oranı
                model.addAttribute("windSpeed", weatherResponse.getWind().getSpeed()); // Rüzgar hızı

                // Hava durumu bilgilerini simgelemek için sınıf adları
                String weatherIcon = "wi wi-owm-" + weatherResponse.getWeather().get(0).getId();
                model.addAttribute("weatherIcon", weatherIcon);
            }
        } catch (Exception e) {
            // geçersiz şehir ismi hatası ekleme
            model.addAttribute("HATA", "Geçersiz şehir adı girdiniz. Lütfen kontrol ederek tekrar deneyin.");
        }

        return "weather"; // hata mesajını hava durumu bilgileri ekranında göster
    }
}