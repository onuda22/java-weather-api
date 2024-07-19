package enigma.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Daily")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String time;
    private Double temperature;
    private Double wind;
    private Integer relativeHumidity;
    private Double rainSum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weather")
    private Weather weather;
}
