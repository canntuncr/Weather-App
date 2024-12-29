package com.example.weather_app.model;

import java.util.List;

/**
 * Hava durumu API'sinden gelen yanıtı modellemek için kullanacağımız sınıfın oluşturulması.
 */
public class WeatherResponse {
    // Şehir adı
    private String name;
    // API için gerekli olan sistem bilgileri örn. ülke bilgisi
    private Sys sys;
    // hava durumu bilgisi listesi
    private List<Weather> weather;
    // Sıcaklık ve nem gibi temel hava durumu verileri
    private Main main;
    // Rüzgar hızı bilgisi
    private Wind wind;

    // Getters ve setters metodları

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * Sistem bilgilerini temsil eden alt sınıf (örneğin, ülke bilgisi).
     */
    public static class Sys {
        // Ülke kodu (örneğin: Türkiye için "TR")
        private String country;

        // Getters ve setters

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    /**
     * Hava durumu bilgilerini temsil eden alt sınıf.
     */
    public static class Weather {
        // Hava durumu ID'si
        private int id;
        // Hava durumu açıklaması (havanın nasıl olduğu bilgisi)
        private String description;

        // Getters ve setters

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * Temel hava durumu bilgilerini temsil eden alt sınıf.
     */
    public static class Main {
        // Sıcaklık bilgisi (°C)
        private double temp;
        // Nem oranı (%)
        private int humidity;

        // Getters ve setters

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }

    /**
     * Rüzgar bilgilerini temsil eden iç sınıf.
     */
    public static class Wind {
        // Rüzgar hızı (km/h)
        private double speed;

        // Getters ve setters

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }
}