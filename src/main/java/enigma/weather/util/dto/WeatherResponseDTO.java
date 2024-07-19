package enigma.weather.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class WeatherResponseDTO {

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("generationtime_ms")
    private double generationtimeMs;

    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;

    @JsonProperty("elevation")
    private int elevation;

    @JsonProperty("daily_units")
    private Map<String, String> dailyUnits;

    @JsonProperty("daily")
    private WeatherDailyDTO daily;
}
