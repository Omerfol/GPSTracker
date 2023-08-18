package com.GPSTracker.gpstracker.Controllers;

import com.GPSTracker.gpstracker.Models.Location;
import com.GPSTracker.gpstracker.Services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("localization")
public class LocalizationController {

    @Autowired
    private LocationService locationService;

    @Operation(summary = "Get a Location list")
    @GetMapping("/")
    public List<Location> fetchLocationList() {
        return locationService.fetchLocationList();
    }

}