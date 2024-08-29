package com.example.driveronboarding.services;

import com.example.driveronboarding.dto.DocumentUploadRequest;
import com.example.driveronboarding.enums.DocumentStatus;
import com.example.driveronboarding.enums.DriverStatus;
import com.example.driveronboarding.helper.DriverHelper;
import com.example.driveronboarding.models.Document;
import com.example.driveronboarding.models.Driver;
import com.example.driveronboarding.repositories.DocumentRepository;
import com.example.driveronboarding.repositories.DriverRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Data
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DriverRepository driverRepository;
    private final DriverHelper driverHelper;

    public Document create(DocumentUploadRequest request){
        Long now = Instant.now().getEpochSecond();
        Driver driver = driverHelper.fetchDriver(request.getDriverId());
        if(!nonNull(driver)){
            return null;
        }
        //Status is set as Accepted by default. In a real application a check has to be in place
        Document document = new Document(
                null, request.getName(), request.getDocId(), request.getUrl(), DocumentStatus.ACCEPTED, driver, now, now);
        documentRepository.save(document);

        System.out.printf("Document: %s created successfully", document);
        return document;
    }

    public boolean areAllDocumentsVerified(Driver driver){
        List<Document> documents = driver.getDocuments();
        for(Document document: documents){
            if(document.getDocumentStatus() != DocumentStatus.ACCEPTED){
                System.out.printf("Document verification not completed. Document: %s", document);
                return false;
            }
        }
        return true;
    }

    public boolean markDocumentsVerified(long driverId){
        Driver driver = driverHelper.fetchDriver(driverId);
        for(Document document:driver.getDocuments()){
            if(document.getDocumentStatus() != DocumentStatus.ACCEPTED){
                System.out.printf("Document: %s is not verified. Please verify and then mark documents verification done", document);
                return false;
            }
        }
        driver.setDocumentVerified(true);
        driver.setDriverStatus(DriverStatus.DOC_VERIFICATION_COMPLETED);
        driver.setUpdatedAt(Instant.now().getEpochSecond());
        driverRepository.save(driver);
        return true;
    }
}
