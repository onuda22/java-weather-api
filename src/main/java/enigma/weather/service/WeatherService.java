package enigma.weather.service;

import enigma.weather.util.dto.WeatherResponseDTO;

public interface WeatherService {
    WeatherResponseDTO getWeatherData(double latitude, double longitude, String startDate, String endDate);
}
