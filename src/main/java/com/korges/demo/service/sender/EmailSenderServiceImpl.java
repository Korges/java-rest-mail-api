package com.korges.demo.service.sender;

import com.korges.demo.model.dto.Error;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.ErrorEnum;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RequiredArgsConstructor
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;

    @Override
    public Either<Error, Email> send(Email email) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email.getRecipients().toArray(String[]::new));
            helper.setSubject(email.getSubject());
            helper.setText(email.getText());
            helper.setPriority(email.getPriority().getValue());

            for (String attachment : email.getAttachments()) {
                FileSystemResource file = new FileSystemResource(new File(attachment));
                helper.addAttachment(attachment, file);
            }

            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(Error.build(email.getId(), ErrorEnum.MAIL_SENDER_ERROR));
        }

        return Either.right(email);
    }
}