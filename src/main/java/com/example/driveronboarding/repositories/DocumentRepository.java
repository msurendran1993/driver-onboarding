package com.example.driveronboarding.repositories;

import com.example.driveronboarding.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
