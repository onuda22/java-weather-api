package enigma.weather.util.dto;

import enigma.weather.model.Daily;
import enigma.weather.model.DailyUnit;
import enigma.weather.model.Weather;

import java.util.ArrayList;
import java.util.List;

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
}
