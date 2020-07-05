package com.korges.demo.service.persistence;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Error;
import com.korges.demo.repository.EmailRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class EmailPersistenceServiceImplTest {

    private EmailPersistenceService service;
    @Mock
    private EmailRepository repository;

    @BeforeEach
    void setUp() {
        this.service = new EmailPersistenceServiceImpl(repository);
    }

    @DisplayName("Test if findById(...) method returns Either.Right")
    @Test
    void test_findById_1() {
        // given
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.of(new Email()));

        // when
        Either<Error, Email> response = service.findById("id");

        // then
        Assertions.assertTrue(response.isRight());
    }

    @DisplayName("Test if findById(...) method returns Either.Left")
    @Test
    void test_findById_2() {
        // given
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.empty());

        // when
        Either<Error, Email> response = service.findById("id");

        // then
        Assertions.assertTrue(response.isLeft());
    }

    @DisplayName("Test if findEmailStatus(...) method returns Either.Right with EmailStatus")
    @Test
    void test_findEmailStatus_1() {
        // given
        Email email = Email.builder().emailStatus(EmailStatus.PENDING).build();
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.of(email));

        // when
        Either<Error, EmailStatus> response = service.findEmailStatus("id");

        // then
        Assertions.assertTrue(response.isRight());
        Assertions.assertEquals(EmailStatus.PENDING, response.get());
    }

    @DisplayName("Test if findEmailStatus(...) method returns Either.Left")
    @Test
    void test_findEmailStatus_2() {
        // given
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.empty());

        // when
        Either<Error, EmailStatus> response = service.findEmailStatus("id");

        // then
        Assertions.assertTrue(response.isLeft());
    }

}