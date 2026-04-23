package com.example.project.E.Pass.System.Backend.service;

import com.example.project.E.Pass.System.Backend.entity.DocumentEntity;
import com.example.project.E.Pass.System.Backend.enums.DocumentType;
import com.example.project.E.Pass.System.Backend.exception.DocumentNotFoundException;
import com.example.project.E.Pass.System.Backend.exception.InvalidDocumentException;
import com.example.project.E.Pass.System.Backend.repository.DocumentRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final GridFSBucket gridFSBucket;
    private final GridFsOperations gridFsOperations;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_CONTENT_TYPES = {
        "image/png",
        "image/jpeg"
    };
    private static final String[] ALLOWED_EXTENSIONS = {
        ".png",
        ".jpg",
        ".jpeg"
    };

    public DocumentEntity uploadDocument(String passId, MultipartFile file, DocumentType documentType) {
        // Validate file
        validateFile(file);

        // Validate document type matches pass type
        validateDocumentType(documentType);

        try {
            // Store file in GridFS
            String gridFsId = gridFsOperations.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
            ).toString();

            // Create document metadata
            DocumentEntity documentEntity = new DocumentEntity(
                passId,
                gridFsId,
                file.getOriginalFilename(),
                getFileExtension(file.getOriginalFilename()),
                file.getSize(),
                documentType,
                file.getContentType()
            );

            return documentRepository.save(documentEntity);
        } catch (IOException e) {
            throw new InvalidDocumentException("Failed to store document: " + e.getMessage());
        }
    }

    public byte[] downloadDocument(String documentId) {
        DocumentEntity documentEntity = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));

        try {
            ObjectId objectId = new ObjectId(documentEntity.getGridFsId());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            gridFSBucket.downloadToStream(objectId, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new DocumentNotFoundException("Failed to download document: " + e.getMessage());
        }
    }

    public byte[] downloadDocumentByPassId(String passId) {
        DocumentEntity documentEntity = documentRepository.findByPassId(passId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found for pass: " + passId));
        return downloadDocument(documentEntity.getId());
    }

    public DocumentEntity getDocumentByPassId(String passId) {
        return documentRepository.findByPassId(passId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found for pass: " + passId));
    }

    public DocumentEntity getDocumentById(String documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidDocumentException("File is empty");
        }

        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidDocumentException("File size exceeds maximum limit of 5MB");
        }

        // Validate content type
        String contentType = file.getContentType();
        if (contentType == null || !Arrays.asList(ALLOWED_CONTENT_TYPES).contains(contentType)) {
            throw new InvalidDocumentException("Invalid file type. Only PNG, JPG, and JPEG files are allowed");
        }

        // Validate file extension
        String fileName = file.getOriginalFilename();
        if (fileName == null || !hasAllowedExtension(fileName)) {
            throw new InvalidDocumentException("Invalid file extension. Only .png, .jpg, and .jpeg files are allowed");
        }
    }

    private void validateDocumentType(DocumentType documentType) {
        if (documentType == null) {
            throw new InvalidDocumentException("Document type is required");
        }
    }

    private boolean hasAllowedExtension(String fileName) {
        return Arrays.stream(ALLOWED_EXTENSIONS)
                .anyMatch(ext -> fileName.toLowerCase().endsWith(ext));
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex).toLowerCase();
        }
        return "";
    }
}
