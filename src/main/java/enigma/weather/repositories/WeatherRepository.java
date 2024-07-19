package enigma.weather.repositories;

import enigma.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    Optional<Weather> findByLatitudeAndLongitudeAndUtcOffsetSeconds(Double latitude, Double longitude, Integer utcOffsetSeconds);
}
