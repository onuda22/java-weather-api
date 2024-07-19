package enigma.weather.repositories;

import enigma.weather.model.DailyUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyUnitRepository extends JpaRepository<DailyUnit, Integer> {
}
