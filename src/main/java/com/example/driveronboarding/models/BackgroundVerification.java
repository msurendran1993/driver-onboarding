package com.example.driveronboarding.models;

import com.example.driveronboarding.enums.BackgroundVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
public class BackgroundVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BackgroundVerificationStatus backgroundVerificationStatus;

    @OneToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = true)
    private long createdAt;

    @Column(nullable = true)
    private long updatedAt;

    public BackgroundVerification() {

    }
}
