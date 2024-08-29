package com.example.driveronboarding.dto;

import com.example.driveronboarding.enums.BackgroundVerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class BackgroundVerificationUpdateRequest {
    private final Long backgroundVerificationId;
    private final Long driverId;
    private final BackgroundVerificationStatus backgroundVerificationStatus;
}

