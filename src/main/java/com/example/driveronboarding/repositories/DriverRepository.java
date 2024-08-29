package com.example.driveronboarding.repositories;

import com.example.driveronboarding.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
