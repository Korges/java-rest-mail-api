package com.korges.demo.service.sender;

import com.korges.demo.model.dto.Error;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.ErrorEnum;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;

    @Override
    public Either<Error, Email> send(Email email) {
        log.info("[EmailSenderServiceImpl] - preparing email message");

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = constructEmail(message, email);
            addAttachments(helper, email);

            log.info("[EmailSenderServiceImpl] - sending email");
            emailSender.send(message);

        } catch (Exception e) {
            log.error("[EmailSenderServiceImpl] - exception during email sending");
            e.printStackTrace();
            return Either.left(Error.build(email.getId(), ErrorEnum.MAIL_SENDER_ERROR));
        }
        log.info("[EmailSenderServiceImpl] - email sent successfully");

        return Either.right(email);
    }

    private MimeMessageHelper constructEmail(MimeMessage message, Email email) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email.getRecipients().toArray(String[]::new));
        helper.setSubject(email.getSubject());
        helper.setText(email.getText());
        helper.setPriority(email.getPriority().getValue());

        return helper;
    }

    private void addAttachments(MimeMessageHelper helper, Email email) throws MessagingException {
        for (String attachment : email.getAttachments()) {
            log.info("[EmailSenderServiceImpl] - adding attachments to email");
            FileSystemResource file = new FileSystemResource(new File(attachment));
            helper.addAttachment(attachment, file);
        }
    }
}