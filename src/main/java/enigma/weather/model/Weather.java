package enigma.weather.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double latitude;
    private Double longitude;
    private Double generationtimeMs;
    private Integer utcOffsetSeconds;
    private String timezone;
    private String timezone_abbreviation;
    private Integer elevation;

    @Embedded
    private DailyUnit dailyUnits;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "weather")
    private List<Daily> daily;

}
