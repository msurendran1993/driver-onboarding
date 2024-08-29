package com.example.driveronboarding.dto;

import lombok.Data;

@Data
public class DocumentUploadRequest {
    private final String name;
    private final String docId;
    private final String url;
    private final Long driverId;
    private Long createdAt;
    private Long updatedAt;

}
