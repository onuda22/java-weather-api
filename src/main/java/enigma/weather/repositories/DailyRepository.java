package enigma.weather.repositories;

import enigma.weather.model.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRepository extends JpaRepository<Daily, Integer> {
}
