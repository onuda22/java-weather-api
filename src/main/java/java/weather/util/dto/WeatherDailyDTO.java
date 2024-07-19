package java.weather.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherDailyDTO {

    @Nullable
    @JsonProperty("time")
    private List<String> time;

    @Nullable
    @JsonProperty("temperature_2m_mean")
    private List<Double> temperature2mMean;

    @Nullable
    @JsonProperty("temperature_10m_mean")
    private List<Double> temperature10mMean;

    @Nullable
    @JsonProperty("relative_humidity_2m_mean")
    private List<Integer> relativeHumidity2mMean;

    @Nullable
    @JsonProperty("rain_sum")
    private List<Double> rainSum;

}
