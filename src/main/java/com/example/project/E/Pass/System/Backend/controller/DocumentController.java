package com.example.project.E.Pass.System.Backend.controller;

import com.example.project.E.Pass.System.Backend.entity.DocumentEntity;
import com.example.project.E.Pass.System.Backend.enums.DocumentType;
import com.example.project.E.Pass.System.Backend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<DocumentEntity> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("passId") String passId,
            @RequestParam("documentType") String documentType,
            @RequestHeader("Authorization") String authHeader) {
        
        DocumentType docType = DocumentType.fromString(documentType);
        DocumentEntity documentEntity = documentService.uploadDocument(passId, file, docType);
        return ResponseEntity.ok(documentEntity);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PASSENGER', 'ADMIN')")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String id) {
        byte[] fileContent = documentService.downloadDocument(id);
        DocumentEntity documentEntity = documentService.getDocumentById(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(documentEntity.getContentType()));
        headers.setContentDispositionFormData("attachment", documentEntity.getFileName());
        headers.setContentLength(fileContent.length);
        
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/pass/{passId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadDocumentByPassId(@PathVariable String passId) {
        byte[] fileContent = documentService.downloadDocumentByPassId(passId);
        DocumentEntity documentEntity = documentService.getDocumentByPassId(passId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(documentEntity.getContentType()));
        headers.setContentDispositionFormData("attachment", documentEntity.getFileName());
        headers.setContentLength(fileContent.length);
        
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/pass/{passId}/metadata")
    @PreAuthorize("hasAnyRole('PASSENGER', 'ADMIN')")
    public ResponseEntity<DocumentEntity> getDocumentMetadataByPassId(@PathVariable String passId) {
        DocumentEntity documentEntity = documentService.getDocumentByPassId(passId);
        return ResponseEntity.ok(documentEntity);
    }
}
