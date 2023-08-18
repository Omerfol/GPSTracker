package com.GPSTracker.gpstracker.Repositories;

import com.GPSTracker.gpstracker.Models.Arduino;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ArduinoControllerRepository {
    public Arduino saveDataFromArduino(@RequestBody Arduino arduino);

    public String getInfoAboutDevice(@RequestParam Integer uid) throws JsonProcessingException;
}
