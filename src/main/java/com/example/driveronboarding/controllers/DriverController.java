package com.example.driveronboarding.controllers;

import com.example.driveronboarding.dto.DriverActivationRequest;
import com.example.driveronboarding.dto.DriverCreateRequest;
import com.example.driveronboarding.models.Driver;
import com.example.driveronboarding.services.DriverService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Data
@RequestMapping("/driver-onboarding/v1")
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/create-driver")
    public ResponseEntity<Map<String, Object>> createDriver(@RequestBody DriverCreateRequest request) {
        Driver driver = driverService.createDriver(request);

        Map<String, Object> response = new HashMap<>();
        response.put("driver", driver);
        return ResponseEntity.ok(response);
    }

    @PutMapping("activate-driver")
    public ResponseEntity<Map<String, Object>> activateDriver(@RequestBody DriverActivationRequest request){
        boolean activated = driverService.activateDriver(request);
        Map<String, Object> response = new HashMap<>();
        if(activated){
            response.put("Data", "driver activated");
            return ResponseEntity.ok(response);
        } else {
            response.put("Status", "driver activation failed");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }

    @GetMapping("get-onboarding-status/{driver_id}")
    public ResponseEntity<Map<String, Object>> getOnboardingStatus(@PathVariable(value = "driver_id") final long driverId){
        Driver driver = driverService.getOnboardingStatus(driverId);
        Map<String, Object> response = new HashMap<>();
        response.put("driverOnboardingDetails", driver);
        return ResponseEntity.ok(response);
    }
}
