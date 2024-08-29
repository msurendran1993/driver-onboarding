package com.example.driveronboarding.models;

import com.example.driveronboarding.enums.DriverStatus;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;

import java.util.List;
import java.util.Map;
import com.example.driveronboarding.helper.JsonbConverter;

@Data
@Entity
@Table(name = "drivers")
@AllArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mobileNo;
    private String email;
    private String aadhar;
    private String dob;

    private DriverStatus driverStatus;

    @Column(columnDefinition = "TEXT")
    private String address;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private BackgroundVerification backgroundVerification;

    @Column(nullable = true)
    private boolean isDocumentVerified;

    @Column(nullable = true)
    private boolean isBackgroundVerified;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonbConverter.class)
    private Map<Object, Object> metadataJson;

    private long createdAt;

    @Column(nullable = true)
    private long updatedAt;

    public Driver() {

    }
}
