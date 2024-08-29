package com.example.driveronboarding.repositories;

import com.example.driveronboarding.models.BackgroundVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BackgroundVerificationRepository extends JpaRepository<BackgroundVerification, Long> {}
