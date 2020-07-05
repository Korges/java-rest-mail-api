package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInput;
import com.korges.demo.model.dto.input.Error;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.ErrorEnum;
import com.korges.demo.model.enums.Priority;
import com.korges.demo.service.persistence.EmailPersistenceService;
import com.korges.demo.service.sender.EmailSenderService;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
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
        EmailInput input = emailInputDTO();
        Mockito.when(persistenceService.findById(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.SENT, Set.of(EMAIL))));

        // when
        Either<Error, Email> response = emailFacadeService.update("id", input);

        // then
        assertTrue(response.isLeft());
        assertEquals(ErrorEnum.SENT, response.getLeft().getError());
    }

    @DisplayName("Test if update(...) method returns Either.Right and is Correct")
    @Test
    void test_update_2() {
        // given
        EmailInput input = emailInputDTO();
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
        assertEquals(ErrorEnum.SENT, response.getLeft().getError());
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
        assertEquals(ErrorEnum.NO_RECIPIENTS, response.getLeft().getError());
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

    @DisplayName("Test if sendAllPending() method returns empty list")
    @Test
    void test_sendAllPending() {
        // given
        Mockito.when(persistenceService.findAllByEmailStatus(EmailStatus.PENDING))
                .thenReturn(io.vavr.collection.List.of());

        List<Either<Error, Email>> response = emailFacadeService.sendAllPending();

        Assertions.assertEquals(0, response.size());
    }

    @DisplayName("Test if sendAllPending() method returns empty list")
    @Test
    void test_sendAllPending_2() {
        // given
        Mockito.when(persistenceService.findAllByEmailStatus(EmailStatus.PENDING))
                .thenReturn(io.vavr.collection.List.of(emailResponse(EmailStatus.PENDING, Set.of())));

        List<Either<Error, Email>> response = emailFacadeService.sendAllPending();

        Assertions.assertEquals(0, response.size());
    }

    @DisplayName("Test if sendAllPending() method returns list with single element")
    @Test
    void test_sendAllPending_3() {
        // given
        Mockito.when(persistenceService.findAllByEmailStatus(EmailStatus.PENDING))
                .thenReturn(io.vavr.collection.List.of(emailResponse(EmailStatus.PENDING, Set.of(EMAIL))));
        Mockito.when(senderService.send(any()))
                .thenReturn(Either.right(emailResponse(EmailStatus.PENDING, Set.of(EMAIL))));
        Mockito.when(persistenceService.save(any()))
                .then(returnsFirstArg());

        // when
        List<Either<Error, Email>> response = emailFacadeService.sendAllPending();

        // then
        assertAll(
                () -> assertEquals(1, response.size()),
                () -> assertEquals("SUBJECT", response.get(0).get().getSubject()),
                () -> assertEquals("TEXT", response.get(0).get().getText()),
                () -> assertEquals(1, response.get(0).get().getRecipients().size()),
                () -> assertEquals(0, response.get(0).get().getAttachments().size()),
                () -> assertEquals(EmailStatus.SENT, response.get(0).get().getEmailStatus()),
                () -> assertEquals(Priority.LOWEST, response.get(0).get().getPriority())
        );
    }

    private EmailInput emailInputDTO() {
        EmailInput input = new EmailInput();
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