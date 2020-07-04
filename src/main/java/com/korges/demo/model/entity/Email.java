package com.korges.demo.model.entity;

import com.korges.demo.model.enums.EmailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
public class Email {
    @Id
    private String id;
    private String header;
    private String message;
    private EmailStatus status;

    public Email() {

    }

    public Email(String id, String header, String message, EmailStatus status) {
        this.id = id;
        this.header = header;
        this.message = message;
        this.status = status;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }
}