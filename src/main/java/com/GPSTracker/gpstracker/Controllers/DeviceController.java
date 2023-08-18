package com.GPSTracker.gpstracker.Controllers;

import com.GPSTracker.gpstracker.Models.Device;
import com.GPSTracker.gpstracker.Services.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Operation(summary = "Get a Device list")
    @GetMapping("")
    public List<Device> getAllDevices() {
        return deviceService.findAll();
    }


    @Operation(summary = "Get a Device by id")
    @GetMapping("/{id}")
    public Object getById(@PathVariable("id") Integer id) {
        return deviceService.findById(id);
    }

    @Operation(summary = "Save a Device")
    @PostMapping("/")
    public Device save(@RequestBody Device device) {
        return deviceService.saveDevice(device);
    }

    @Operation(summary = "Update a Device")
    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable Integer id, @RequestBody Device device) {
       return deviceService.updateDevice(id,device);
    }


}
