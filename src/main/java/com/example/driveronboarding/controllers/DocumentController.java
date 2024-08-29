package com.example.driveronboarding.controllers;


import com.example.driveronboarding.dto.DocumentUploadRequest;
import com.example.driveronboarding.dto.DriverCreateRequest;
import com.example.driveronboarding.models.Document;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.driveronboarding.services.DocumentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/driver-onboarding/v1")
@Data
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/upload-document")
    public ResponseEntity<Map<String, Object>> uploadDocument(@RequestBody DocumentUploadRequest request){
        Document document = documentService.create(request);

        Map<String, Object> response = new HashMap<>();
        response.put("Document", document);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/mark-documents-verified/{driver_id}")
    public ResponseEntity<Map<String, Object>> markDocumentsVerified(@PathVariable (value = "driver_id") long driverId){
        boolean verified = documentService.markDocumentsVerified(driverId);
        Map<String, Object> response = new HashMap<>();
        if(verified){
            response.put("Status", "Success");
        } else {
            response.put("Status", "Not all documents are verified. Verify all and then only try to mark all are verified");
        }

        return ResponseEntity.ok(response);
    }
}
