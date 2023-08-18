package com.GPSTracker.gpstracker.Controllers;

import com.GPSTracker.gpstracker.Mapper.ConAndLocMapperImpl;
import com.GPSTracker.gpstracker.Models.Arduino;
import com.GPSTracker.gpstracker.Models.DeviceInfo;
import com.GPSTracker.gpstracker.Services.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("arduino")
@Slf4j
public class ArduinoController {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SaveService saveService;
    @Autowired
    private ConAndLocMapperImpl conAndLocMapper;

    @Operation(summary = "Receive location and connection data, save it to different tables in DB, check status and sends notification about changes")
    @PostMapping("/localization/")
    public void saveDataFromArduino(@RequestParam Integer uid, @RequestBody Arduino arduino) {
        saveService.saveConAndLocObjectToDB(uid, conAndLocMapper.arduinoDataToLocationObject(arduino,uid), conAndLocMapper.arduinoDataToConnectionObject(arduino,uid), arduino);
        log.info("Device uid: {}. Data to save from arduino: {}",uid, String.valueOf(arduino));
    }

    @Operation(summary = "Sends information's to device about intervals, time, heartbeat rate and fencing range")
    @GetMapping("/getDeviceConfiguration/")
    @ResponseBody
    public DeviceInfo sendInformationToArduinoAboutDevice(@RequestParam Integer uid){
        return deviceService.getDeviceInfo(uid);
    }
}
