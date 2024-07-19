package enigma.weather.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import enigma.weather.service.WeatherService;
import enigma.weather.util.dto.WeatherResponseDTO;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final RestClient restClient;

    public WeatherServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public WeatherResponseDTO getWeatherData(double latitude, double longitude, String startDate, String endDate) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://climate-api.open-meteo.com/v1/climate")
                    .queryParam("latitude", latitude)
                    .queryParam("longitude", longitude)
                    .queryParam("start_date", startDate)
                    .queryParam("end_date", endDate)
                    .queryParam("models", "NICAM16_8S")
                    .queryParam("timezone", "Asia/Bangkok")
                    .queryParam("disable_bias_correction", true)
                    .queryParam("daily", "temperature_2m_mean,wind_speed_10m_mean,relative_humidity_2m_mean,rain_sum")
                    .toUriString();

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(WeatherResponseDTO.class);
        } catch (RestClientException e) {
            // Handle exceptions
            throw new RestClientException(e.getMessage());
        }
    }
}
