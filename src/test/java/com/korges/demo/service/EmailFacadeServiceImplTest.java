package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Error;
import com.korges.demo.model.enums.Priority;
import com.korges.demo.service.persistence.EmailPersistenceService;
import com.korges.demo.service.sender.EmailSenderService;
import io.vavr.control.Either;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmailFacadeServiceImplTest {
    private static final String EMAIL = "some@mail.com";

    @Mock
    private EmailPersistenceService persistenceService;
    @Mock
    private EmailSenderService senderService;
    @InjectMocks
    private EmailFacadeServiceImpl emailFacadeService;

    @DisplayName("Test if update(...) method returns Either.Left")
    @Test
    void test_update() {
        // given
        EmailInputDTO input = emailInputDTO();
        Mockito.when(persistenceService.findById(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.SENT, Set.of(EMAIL))));

        // when
        Either<Error, Email> response = emailFacadeService.update("id", input);

        // then
        assertTrue(response.isLeft());
        assertEquals(Error.SENT, response.getLeft());
    }

    @DisplayName("Test if update(...) method returns Either.Right and is Correct")
    @Test
    void test_update_2() {
        // given
        EmailInputDTO input = emailInputDTO();
        Mockito.when(persistenceService.save(any())).then(returnsFirstArg());
        Mockito.when(persistenceService.findById(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.PENDING, Set.of(EMAIL))));

        // when
        Either<Error, Email> response = emailFacadeService.update("id", input);

        // then
        assertAll(
                () -> assertTrue(response.isRight()),
                () -> assertEquals("SUBJECT", response.get().getSubject()),
                () -> assertEquals("TEXT", response.get().getText()),
                () -> assertEquals(0, response.get().getRecipients().size()),
                () -> assertEquals(0, response.get().getAttachments().size()),
                () -> assertEquals(EmailStatus.PENDING, response.get().getEmailStatus()),
                () -> assertEquals(Priority.LOWEST, response.get().getPriority())
        );
    }

    @DisplayName("Test if send(...) method returns Either.Left")
    @Test
    void test_send() {
        //given
        Mockito.when(persistenceService.findById(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.SENT, Set.of())));

        // when
        Either<Error, Email> response = emailFacadeService.send("id");

        // then
        assertTrue(response.isLeft());
        assertEquals(Error.SENT, response.getLeft());
    }

    @DisplayName("Test if send(...) method returns Either.Left")
    @Test
    void test_send_2() {
        //given
        Mockito.when(persistenceService.findById(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.PENDING, Set.of())));

        // when
        Either<Error, Email> response = emailFacadeService.send("id");

        // then
        assertTrue(response.isLeft());
        assertEquals(Error.NO_RECIPIENTS, response.getLeft());
    }

    @DisplayName("Test if send(...) method returns Either.Right and is Correct")
    @Test
    void test_send_3() {
        //given
        Mockito.when(persistenceService.findById(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.PENDING, Set.of(EMAIL))));
        Mockito.when(senderService.send(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.PENDING, Set.of(EMAIL))));
        Mockito.when(persistenceService.save(any()))
                .then(returnsFirstArg());

        // when
        Either<Error, Email> response = emailFacadeService.send("id");

        // then
        assertAll(
                () -> assertTrue(response.isRight()),
                () -> assertEquals("SUBJECT", response.get().getSubject()),
                () -> assertEquals("TEXT", response.get().getText()),
                () -> assertEquals(1, response.get().getRecipients().size()),
                () -> assertEquals(0, response.get().getAttachments().size()),
                () -> assertEquals(EmailStatus.SENT, response.get().getEmailStatus()),
                () -> assertEquals(Priority.LOWEST, response.get().getPriority())
        );
    }

    private EmailInputDTO emailInputDTO() {
        EmailInputDTO input = new EmailInputDTO();
        input.setSubject("SUBJECT");
        input.setText("TEXT");

        return input;
    }

    private Email emailResponse(EmailStatus status, Set<String> recipients) {
        return Email.builder()
                .id("ID")
                .subject("SUBJECT")
                .text("TEXT")
                .recipients(recipients)
                .attachments(Set.of())
                .emailStatus(status)
                .priority(Priority.LOWEST)
                .build();
    }
}