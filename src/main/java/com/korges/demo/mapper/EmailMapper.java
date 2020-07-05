package com.korges.demo.mapper;

import com.korges.demo.model.dto.EmailInput;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;

public class EmailMapper {

    private EmailMapper() {
    }

    public static Email mapEmailInputToEmail(EmailInput emailInput) {
        return Email.builder()
                .subject(emailInput.getSubject())
                .text(emailInput.getText())
                .recipients(emailInput.getRecipients())
                .attachments(emailInput.getAttachments())
                .emailStatus(EmailStatus.PENDING)
                .priority(emailInput.getPriority())
                .build();
    }

    public static Email updateEmail(Email email, EmailInput emailInput) {
        return Email.builder()
                .id(email.getId())
                .subject(emailInput.getSubject())
                .text(emailInput.getText())
                .recipients(emailInput.getRecipients())
                .attachments(emailInput.getAttachments())
                .emailStatus(email.getEmailStatus())
                .priority(emailInput.getPriority())
                .build();
    }

    public static Email setEmailStatusToSent(Email email) {
        return Email.builder()
                .id(email.getId())
                .subject(email.getSubject())
                .text(email.getText())
                .recipients(email.getRecipients())
                .attachments(email.getAttachments())
                .emailStatus(EmailStatus.SENT)
                .priority(email.getPriority())
                .build();
    }
}
