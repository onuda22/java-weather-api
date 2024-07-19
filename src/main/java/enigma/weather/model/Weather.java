package enigma.weather.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne
    private DailyUnit dailyUnit;

    @OneToOne
    private Daily daily;
}
