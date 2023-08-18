package com.GPSTracker.gpstracker.Controllers;
import com.GPSTracker.gpstracker.Services.OpenStreetMapService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("osm")
@RestController
public class OpenStreetMapController {

    @Autowired
    private OpenStreetMapService openStreetMapService;

    @GetMapping("/getLastDevicePosition/")
    @ResponseBody
    public String getLastDevicePosition(@RequestParam Integer uid) {
        return openStreetMapService.generateOMSUrl(uid);
    }

    @GetMapping("/map/")
    @RequestBody
    public String getDevicePathMap(@RequestParam Integer uid) {
        return openStreetMapService.generatePathMap(uid);
    }
}
