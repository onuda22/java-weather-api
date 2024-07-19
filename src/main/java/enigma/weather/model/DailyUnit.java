package enigma.weather.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyUnit {

    private String time = "iso8601";
    private String temperature = "°C";
    private String windSpeed = "km/h";
    private String relativeHumidity = "%";
    private String rainSum = "mm";
}
