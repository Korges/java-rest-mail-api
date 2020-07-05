package com.korges.demo.model.entity;

import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Setter @Getter
@Builder
@Document
public class Email {

    @Id
    private String id;
    private String subject;
    private String text;
    private Set<String> recipients;
    private Set<String> attachments;
    private EmailStatus emailStatus;
    private Priority priority;

    public Email() {

    }

    public Email(String id, String subject, String text, Set<String> recipients,
                 Set<String> attachments, EmailStatus emailStatus, Priority priority) {
        this.id = id;
        this.subject = subject;
        this.text = text;
        this.recipients = recipients;
        this.attachments = attachments;
        this.emailStatus = emailStatus;
        this.priority = priority;
    }
}