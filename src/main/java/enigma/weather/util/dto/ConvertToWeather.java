package enigma.weather.util.dto;

import enigma.weather.model.Daily;
import enigma.weather.model.DailyUnit;
import enigma.weather.model.Weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertToWeather {
    public static Weather convertToWeather(WeatherResponseDTO dto) throws Exception{
        List<Daily> dailyList = new ArrayList<>();
        List<String> times = dto.getDaily().getTime();
        List<Double> temperatures = dto.getDaily().getTemperature2mMean();
        List<Double> winds = dto.getDaily().getWindSpeed10mMean();
        List<Integer> relativeHumidities = dto.getDaily().getRelativeHumidity2mMean();
        List<Double> rainSums = dto.getDaily().getRainSum();

        Weather weather = Weather.builder()
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .generationtimeMs(dto.getGenerationtimeMs())
                .utcOffsetSeconds(dto.getUtcOffsetSeconds())
                .timezone(dto.getTimezone())
                .timezone_abbreviation(dto.getTimezoneAbbreviation())
                .elevation(dto.getElevation())
                .dailyUnits(new DailyUnit())
                .daily(dailyList)
                .build();

        try {
            for (int i = 0; i < times.size(); i++){
                Daily daily = Daily.builder()
                        .time(times.get(i))
                        .temperature(temperatures.get(i))
                        .wind(winds.get(i))
                        .relativeHumidity(relativeHumidities.get(i))
                        .rainSum(rainSums.get(i))
                        .weather(weather)
                        .build();

                dailyList.add(daily);
            }
            return weather;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static WeatherResponseDTO convertToWeatherResponseDTO(Weather weather) {
        WeatherResponseDTO dto = new WeatherResponseDTO();
        dto.setLatitude(weather.getLatitude());
        dto.setLongitude(weather.getLongitude());
        dto.setGenerationtimeMs(weather.getGenerationtimeMs());
        dto.setUtcOffsetSeconds(weather.getUtcOffsetSeconds());
        dto.setTimezone(weather.getTimezone());
        dto.setTimezoneAbbreviation(weather.getTimezone_abbreviation());
        dto.setElevation(weather.getElevation());

        Map<String, String> dailyUnits = new HashMap<>();
        dailyUnits.put("time", weather.getDailyUnits().getTime());
        dailyUnits.put("temperature_2m_mean", weather.getDailyUnits().getTemperature());
        dailyUnits.put("wind_speed_10m_mean", weather.getDailyUnits().getWindSpeed());
        dailyUnits.put("relative_humidity_2m_mean", weather.getDailyUnits().getRelativeHumidity());
        dailyUnits.put("rain_sum", weather.getDailyUnits().getRainSum());
        dto.setDailyUnits(dailyUnits);

        WeatherDailyDTO dailyDTO = new WeatherDailyDTO();
        List<String> times = weather.getDaily().stream().map(Daily::getTime).collect(Collectors.toList());
        List<Double> temperatures = weather.getDaily().stream().map(Daily::getTemperature).collect(Collectors.toList());
        List<Double> winds = weather.getDaily().stream().map(Daily::getWind).collect(Collectors.toList());
        List<Integer> relativeHumidities = weather.getDaily().stream().map(Daily::getRelativeHumidity).collect(Collectors.toList());
        List<Double> rainSums = weather.getDaily().stream().map(Daily::getRainSum).collect(Collectors.toList());

        dailyDTO.setTime(times);
        dailyDTO.setTemperature2mMean(temperatures);
        dailyDTO.setWindSpeed10mMean(winds);
        dailyDTO.setRelativeHumidity2mMean(relativeHumidities);
        dailyDTO.setRainSum(rainSums);

        dto.setDaily(dailyDTO);

        return dto;
    }
}
