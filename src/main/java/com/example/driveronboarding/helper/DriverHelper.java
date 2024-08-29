package com.example.driveronboarding.helper;

import com.example.driveronboarding.models.Driver;
import com.example.driveronboarding.repositories.DriverRepository;
import lombok.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public class DriverHelper {

    private final DriverRepository driverRepository;
    @Cacheable(value = "driver-onboarding")
    public Driver fetchDriver(Long id){
        Driver driver;
        Optional<Driver> driverOpt = driverRepository.findById(id);
        if(driverOpt.isPresent()){
            driver = driverOpt.get();
            return driver;
        } else{
            System.out.printf("Invalid driverID: %s", id);
            return null;
        }
    }
}
