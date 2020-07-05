package com.korges.demo.service.sender;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;

    // TODO
    @Override
    public Either<Error, Email> send(Email email) {
        if (email.getRecipients().isEmpty()) return Either.left(Error.NO_RECIPIENTS);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(email.getSubject());
        message.setText(email.getText());
        message.setTo(email.getRecipients().toJavaArray(String[]::new));

        emailSender.send(message);

        return Either.right(email);
    }
}