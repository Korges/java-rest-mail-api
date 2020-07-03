package com.korges.demo.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document
public class Email {
    @Id
    private String id;
    private String header;
    private String message;
}