package com.korges.demo.service.sender;

import com.korges.demo.model.dto.Error;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Priority;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {

    private EmailSenderService service;
    @Mock
    private JavaMailSender emailSender;
    @Mock
    private MimeMessage message;

    @BeforeEach
    void setUp() {
        this.service = new EmailSenderServiceImpl(emailSender);
    }

    @DisplayName("Test if send(...) method returns Either Left")
    @Test
    void test_send_1() {
        // given
        Mockito.doThrow(new RuntimeException()).when(emailSender).send(any(MimeMessage.class));
        Mockito.when(emailSender.createMimeMessage()).thenReturn(message);

        // when
        Either<Error, Email> response = service.send(buildEmail());

        // then
        Assertions.assertTrue(response.isLeft());
    }

    @DisplayName("Test if send(...) method returns Either Right")
    @Test
    void test_send_2() {
        Mockito.when(emailSender.createMimeMessage()).thenReturn(message);

        // when
        Either<Error, Email> response = service.send(buildEmail());

        // then
        Assertions.assertTrue(response.isRight());
    }

    private Email buildEmail() {
        return Email.builder()
                .id("ID")
                .subject("SUBJECT")
                .text("TEXT")
                .recipients(Set.of())
                .attachments(Set.of())
                .emailStatus(EmailStatus.PENDING)
                .priority(Priority.LOWEST)
                .build();
    }

}