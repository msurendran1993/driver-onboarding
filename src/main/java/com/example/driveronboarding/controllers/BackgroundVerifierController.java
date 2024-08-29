package com.example.driveronboarding.controllers;

import com.example.driveronboarding.dto.BackgroundVerificationRequest;
import com.example.driveronboarding.dto.BackgroundVerificationUpdateRequest;
import com.example.driveronboarding.models.BackgroundVerification;
import com.example.driveronboarding.services.BackgroundVerifierService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/driver-onboarding/v1")
@Data
public class BackgroundVerifierController {
    private final BackgroundVerifierService backgroundVerifierService;

    @PostMapping("/trigger-background-verification")
    public ResponseEntity<Map<String, Object>> triggerBackgroundVerification(@RequestBody BackgroundVerificationRequest request){
        BackgroundVerification backgroundVerification = backgroundVerifierService.triggerBackgroundVerification(request);

        Map<String, Object> response = new HashMap<>();
        response.put("Data", backgroundVerification);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-background-verification-status")
    public ResponseEntity<Map<String, Object>> updateBackgroundVerificationStatus(@RequestBody BackgroundVerificationUpdateRequest request){
        BackgroundVerification backgroundVerification = backgroundVerifierService.updateBackgroundVerificationStatus(request);

        Map<String, Object> response = new HashMap<>();
        response.put("Background Verification", backgroundVerification);
        return ResponseEntity.ok(response);
    }
}
