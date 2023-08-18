package com.GPSTracker.gpstracker.Controllers;



import com.GPSTracker.gpstracker.Services.HeartbeatStatesServices;
import io.swagger.v3.oas.annotations.Operation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("arduino")
public class HeartbeatController {


    @Autowired
    HeartbeatStatesServices heartBeatStates;


    @Operation(summary = "Every 30s. receives hearbeat status information from the device")

    @PostMapping("/heartbeat/")
    public void updateHeartbeatState(@RequestParam Integer uid) {
        heartBeatStates.updateState(uid);
    }
}
