package com.example.driveronboarding.services;

import com.example.driveronboarding.dto.BackgroundVerificationRequest;
import com.example.driveronboarding.dto.BackgroundVerificationUpdateRequest;
import com.example.driveronboarding.enums.BackgroundVerificationStatus;
import com.example.driveronboarding.enums.DriverStatus;
import com.example.driveronboarding.helper.DriverHelper;
import com.example.driveronboarding.models.BackgroundVerification;
import com.example.driveronboarding.models.Driver;
import com.example.driveronboarding.repositories.BackgroundVerificationRepository;
import com.example.driveronboarding.repositories.DriverRepository;
import lombok.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Data
public class BackgroundVerifierService {
    private BackgroundVerificationRepository backgroundVerificationRepository;
    private DriverRepository driverRepository;
    private final DriverHelper driverHelper;

    public BackgroundVerification triggerBackgroundVerification(BackgroundVerificationRequest request){
        Driver driver = driverHelper.fetchDriver(request.getDriverId());
        long now = Instant.now().getEpochSecond();
        BackgroundVerification verification = new BackgroundVerification(null, BackgroundVerificationStatus.IN_PROGRESS, driver, now, now);
        backgroundVerificationRepository.save(verification);
        System.out.printf("Background verification request triggered successfully. %s", verification);
        return verification;
    }

    public BackgroundVerification updateBackgroundVerificationStatus(BackgroundVerificationUpdateRequest request){
        Long id = request.getBackgroundVerificationId();
        Driver driver = driverHelper.fetchDriver(request.getDriverId());

        BackgroundVerification verification = fetchBackgroundVerification(id);
        verification.setBackgroundVerificationStatus(request.getBackgroundVerificationStatus());
        verification.setUpdatedAt(Instant.now().getEpochSecond());
        backgroundVerificationRepository.save(verification);

        DriverStatus driverStatus = request.getBackgroundVerificationStatus().equals(BackgroundVerificationStatus.SUCCEEDED) ?
                DriverStatus.BACKGROUND_VERIFICATION_COMPLETED : DriverStatus.BACKGROUND_VERIFICATION_FAILED;
        driver.setDriverStatus(driverStatus);
        driver.setUpdatedAt(Instant.now().getEpochSecond());
        driverRepository.save(driver);
        return verification;
    }

    @Cacheable(value = "driver-onboarding")
    public BackgroundVerification fetchBackgroundVerification(Long id){
        BackgroundVerification verification;
        Optional<BackgroundVerification> verificationOpt = backgroundVerificationRepository.findById(id);
        if(verificationOpt.isPresent()){
            verification = verificationOpt.get();
            return verification;
        } else{
            System.out.printf("Invalid verification ID: %s", id);
            return null;
        }
    }

    public boolean isBackgroundVerificationPassed(Driver driver){
        BackgroundVerification backgroundVerification = driver.getBackgroundVerification();
        return backgroundVerification.getBackgroundVerificationStatus() == BackgroundVerificationStatus.SUCCEEDED;
    }
}
