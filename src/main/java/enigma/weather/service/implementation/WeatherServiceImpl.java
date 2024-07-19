package enigma.weather.service.implementation;

import enigma.weather.model.Daily;
import enigma.weather.model.Weather;
import enigma.weather.repositories.WeatherRepository;
import enigma.weather.util.dto.ConvertToWeather;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import enigma.weather.service.WeatherService;
import enigma.weather.util.dto.WeatherResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final RestClient restClient;
    private final WeatherRepository weatherRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public WeatherServiceImpl(RestClient restClient, WeatherRepository weatherRepository, RedisTemplate<String, Object> redisTemplate) {
        this.restClient = restClient;
        this.weatherRepository = weatherRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    //kalo pake cacheManager bisa pakai anotasi @Cacheable
//    @Cacheable(value = "weatherCache", key = "#latitude + '-' + #longitude + '-' + #startDate + '-' + #endDate")
    public WeatherResponseDTO getWeatherData(double latitude, double longitude, String startDate, String endDate) {
        String cacheKey = latitude + "-" + longitude + "-" + startDate + "-" + endDate;
        WeatherResponseDTO cachedWeather = (WeatherResponseDTO) redisTemplate.opsForValue().get(cacheKey);

        if (cachedWeather != null) {
            return cachedWeather;
        }

        Optional<Weather> existingWeather = weatherRepository.findByLatitudeAndLongitudeAndUtcOffsetSeconds(
                latitude, longitude, 0);

        if (existingWeather.isPresent()) {
            WeatherResponseDTO responseDTO = ConvertToWeather.convertToWeatherResponseDTO(existingWeather.get());
            redisTemplate.opsForValue().set(cacheKey, responseDTO, 1, TimeUnit.MINUTES);
            return responseDTO;
        } else {
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

                WeatherResponseDTO dto = restClient.get()
                        .uri(url)
                        .retrieve()
                        .body(WeatherResponseDTO.class);

                Weather result = ConvertToWeather.convertToWeather(dto);
                updateWeatherData(result);

                redisTemplate.opsForValue().set(cacheKey, dto, 1, TimeUnit.MINUTES);

                return dto;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateWeatherData(Weather weather) {
        Optional<Weather> existingWeather = weatherRepository.findByLatitudeAndLongitudeAndUtcOffsetSeconds(
                weather.getLatitude(), weather.getLongitude(), weather.getUtcOffsetSeconds());

        if (existingWeather.isPresent()) {
            Weather weatherToUpdate = existingWeather.get();
            List<Daily> existingDailyData = weatherToUpdate.getDaily();
            List<Daily> newDailyData = weather.getDaily();

            // Update existing weather fields
            weatherToUpdate.setGenerationtimeMs(weather.getGenerationtimeMs());
            weatherToUpdate.setTimezone(weather.getTimezone());
            weatherToUpdate.setTimezone_abbreviation(weather.getTimezone_abbreviation());
            weatherToUpdate.setElevation(weather.getElevation());
            weatherToUpdate.setDailyUnits(weather.getDailyUnits());

            // Add new daily entries
            for (Daily newDaily : newDailyData) {
                if (existingDailyData.stream().noneMatch(d -> d.getTime().equals(newDaily.getTime()))) {
                    newDaily.setWeather(weatherToUpdate);
                    existingDailyData.add(newDaily);
                }
            }
            weatherRepository.save(weatherToUpdate);
        } else {
            weatherRepository.save(weather);
        }
    }
}
