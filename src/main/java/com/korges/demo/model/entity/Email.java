package com.korges.demo.model.entity;

import com.korges.demo.model.enums.EmailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@Document
public class Email {
    @Id
    private String id;
    private String header;
    private String message;
    private List<String> recipients;
    private EmailStatus status;

    public Email() {

    }

}