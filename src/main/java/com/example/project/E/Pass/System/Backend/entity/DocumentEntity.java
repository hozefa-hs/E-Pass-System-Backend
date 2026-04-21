package com.example.project.E.Pass.System.Backend.entity;

import com.example.project.E.Pass.System.Backend.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {

    @Id
    private String id;

    private String passId;

    private String gridFsId; // GridFS file ID

    private String fileName;

    private String fileType; // PDF or PNG

    private long fileSize; // in bytes

    private DocumentType documentType;

    private String contentType;

    @CreatedDate
    private LocalDateTime uploadDate;

    public DocumentEntity(String passId, String gridFsId, String fileName, String fileType,
                          long fileSize, DocumentType documentType, String contentType) {
        this.passId = passId;
        this.gridFsId = gridFsId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.documentType = documentType;
        this.contentType = contentType;
        this.uploadDate = LocalDateTime.now();
    }

    public String getFileSizeInMB() {
        return String.format("%.2f MB", fileSize / (1024.0 * 1024.0));
    }
}
