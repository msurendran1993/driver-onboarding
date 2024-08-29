package com.example.driveronboarding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude( JsonInclude.Include.NON_EMPTY)
public class DriverCreateRequest {
    private String name;
    private String mobileNo;
    private String email;
    private String aadhar;
    private String dob;
    private String address;

}
