package enigma.weather.controller;

import enigma.weather.service.WeatherService;
import enigma.weather.util.dto.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/today")
    public ResponseEntity<?> getToday(@RequestParam Double latitude, @RequestParam Double longitude,
                                      @RequestParam String startDate, @RequestParam String endDate){
        WeatherResponseDTO responseDTO = weatherService.getWeatherData(latitude, longitude, startDate, endDate);
        return ResponseEntity.ok(responseDTO);
    }
}
