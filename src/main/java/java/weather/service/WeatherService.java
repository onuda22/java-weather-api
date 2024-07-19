package java.weather.service;

import java.weather.util.dto.WeatherResponseDTO;

public interface WeatherService {
    WeatherResponseDTO getWeatherData(double latitude, double longitude, String startDate, String endDate);
}
