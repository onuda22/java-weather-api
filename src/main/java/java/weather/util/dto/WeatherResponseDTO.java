package java.weather.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class WeatherResponseDTO {

    @JsonProperty()
    private double latitude;

    private double longitude;

    private double generationtimeMs;

    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;

    private String timezone;

    @JsonProperty("daily_units")
    private Map<String, String> dailyUnits;

    @JsonProperty("daily_units")
    private WeatherDailyDTO daily;
}
