package com.example.driveronboarding.services;

import com.example.driveronboarding.dto.DriverActivationRequest;
import com.example.driveronboarding.dto.DriverCreateRequest;
import com.example.driveronboarding.enums.DriverStatus;
import com.example.driveronboarding.models.Driver;
import com.example.driveronboarding.repositories.DriverRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import java.time.Instant;
import com.example.driveronboarding.helper.DriverHelper;

@Data
@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DocumentService documentService;
    private final BackgroundVerifierService backgroundVerifierService;
    private final DriverHelper driverHelper;

    public Driver createDriver(DriverCreateRequest request){
        //validate
        long now = Instant.now().getEpochSecond();
        Driver driver = new Driver(null, request.getName(),
                                    request.getMobileNo(),
                                    request.getEmail(),
                                    request.getAadhar(),
                                    request.getDob(),
                                    DriverStatus.CREATED,
                                    request.getAddress(),
                                    null, null,
                                    false, false, null,
                                     now, now);
        driverRepository.save(driver);
        return driver;
    }

    public boolean activateDriver(DriverActivationRequest request){
        Driver driver = driverHelper.fetchDriver(request.getDriverId());
        if(!canActivateDriver(driver)){
            System.out.printf("Driver can't be activated. Driver: %s", driver); //Why add msg
            return false;
        }
        driver.setDriverStatus(DriverStatus.ACTIVE);
        driver.setUpdatedAt(Instant.now().getEpochSecond());
        driverRepository.save(driver);
        System.out.printf("Driver activated successfully. Driver: %s", driver);
        return true;
    }

    public Driver getOnboardingStatus(long driverId){
        return driverHelper.fetchDriver(driverId);
    }

    private boolean canActivateDriver(Driver driver){
        return areAllDocumentsVerified(driver) && isBackgroundCheckSucceeded(driver); // Add any more checks if needed
    }

    private boolean areAllDocumentsVerified(Driver driver){
        boolean verified = documentService.areAllDocumentsVerified(driver);
        System.out.printf("Document verification status: %s", verified);
        return verified;
    }

    private boolean isBackgroundCheckSucceeded(Driver driver){
        boolean verified = backgroundVerifierService.isBackgroundVerificationPassed(driver);
        System.out.printf("Document verification status: %s", verified);
        return verified;
    }
}

