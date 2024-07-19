package enigma.weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Daily")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private List<String> time;
    private List<Double> temperature;
    private List<Double> wind;
    private List<Integer> relativeHumidity;
    private List<Integer> rainSum;
}
