package com.example.driveronboarding.models;

import com.example.driveronboarding.enums.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String docId;
    private String url;
    private DocumentStatus documentStatus;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    private Long createdAt;
    private Long updatedAt;

    public Document() {

    }
}
