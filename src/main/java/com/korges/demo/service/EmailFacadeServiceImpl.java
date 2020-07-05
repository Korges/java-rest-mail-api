package com.korges.demo.service;

import com.korges.demo.mapper.EmailMapper;
import com.korges.demo.model.dto.EmailInput;
import com.korges.demo.model.dto.Error;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.ErrorEnum;
import com.korges.demo.service.persistence.EmailPersistenceService;
import com.korges.demo.service.sender.EmailSenderService;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import static com.korges.demo.mapper.EmailMapper.mapEmailInputToEmail;
import static com.korges.demo.mapper.EmailMapper.updateEmail;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailFacadeServiceImpl implements EmailFacadeService {
    private final EmailPersistenceService emailPersistenceService;
    private final EmailSenderService emailSenderService;

    private final Predicate<Email> isEmailPending =  email -> email.getEmailStatus().equals(EmailStatus.PENDING);
    private final Predicate<Email> isRecipientProvided = email -> !email.getRecipients().isEmpty();

    @Override
    public List<Email> findAll() {
        log.info("[EmailFacadeServiceImpl] - fetching all emails");

        return emailPersistenceService.findAll();
    }

    @Override
    public Either<Error, Email> findById(String id) {
        log.info("[EmailFacadeServiceImpl] - fetching email by id: " + id);

        return emailPersistenceService.findById(id);
    }

    @Override
    public Email save(EmailInput emailInput) {
        log.info("[EmailFacadeServiceImpl] - saving new email: " + emailInput);
        Email email = mapEmailInputToEmail(emailInput);

        return emailPersistenceService.save(email);
    }

    @Override
    public Either<Error, Email> update(String id, EmailInput emailInput) {
        log.info("[EmailFacadeServiceImpl] - updating email by id: " + id + " with: " + emailInput);

        return emailPersistenceService.findById(id)
                .filterOrElse(x -> x.getEmailStatus().equals(EmailStatus.PENDING), x -> Error.build(id, ErrorEnum.SENT))
                .map(email -> updateEmail(email, emailInput))
                .map(emailPersistenceService::save);
    }

    @Override
    public Either<Error, Email> send(String id) {
        log.info("[EmailFacadeServiceImpl] - sending email by id: " + id);

        return emailPersistenceService.findById(id)
                .filterOrElse(isEmailPending, x -> Error.build(id, ErrorEnum.SENT))
                .filterOrElse(isRecipientProvided, x -> Error.build(id, ErrorEnum.NO_RECIPIENTS))
                .flatMap(emailSenderService::send)
                .map(EmailMapper::setEmailStatusToSent)
                .map(emailPersistenceService::save);
    }

    @Override
    public List<Either<Error, Email>> sendAllPending() {
        log.info("[EmailFacadeServiceImpl] - sending all pending emails");

        return emailPersistenceService.findAllByEmailStatus(EmailStatus.PENDING)
                .filter(isRecipientProvided)
                .map(x -> emailSenderService
                                .send(x)
                                .map(EmailMapper::setEmailStatusToSent)
                                .map(emailPersistenceService::save)
                );
    }

    @Override
    public Either<Error, EmailStatus> findEmailStatus(String id) {
        log.info("[EmailFacadeServiceImpl] - fetching email status by id: " + id);

        return emailPersistenceService.findEmailStatus(id);
    }

}