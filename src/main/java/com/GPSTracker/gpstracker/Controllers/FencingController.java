package com.GPSTracker.gpstracker.Controllers;




import com.GPSTracker.gpstracker.Functions.CheckAlarmStatus;
import com.GPSTracker.gpstracker.Models.FencingStates;
import com.GPSTracker.gpstracker.Services.FencingStatesServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FencingController {

    @Autowired
    FencingStatesServices fencingService;

    @Operation(summary = "Every 15s. receives fencing status information from the device")
    @PostMapping("/arduino/fencing/")
    public void updateFencingState(@RequestParam Integer uid, @RequestBody FencingStates fencing) {
        fencingService.updateState(uid,fencing);
    }
}
