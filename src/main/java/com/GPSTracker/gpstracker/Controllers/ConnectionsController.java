package com.GPSTracker.gpstracker.Controllers;

import com.GPSTracker.gpstracker.Models.Connection;
import com.GPSTracker.gpstracker.Services.ConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("connection")
public class ConnectionsController {

    @Autowired
    private ConnectionService connectionService;

    @Operation(summary = "Get a Location list")
    @GetMapping("/")
    public List<Connection> fetchConnectionsList() {
        return connectionService.fetchConnectionsList();
    }

}
