package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Error;
import com.korges.demo.service.persistence.EmailPersistenceService;
import com.korges.demo.service.sender.EmailSenderService;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class EmailFacadeServiceImpl implements EmailFacadeService {
    private final EmailPersistenceService emailPersistenceService;
    private final EmailSenderService emailSenderService;

    private final Predicate<Email> isEmailPending =  email -> email.getEmailStatus().equals(EmailStatus.PENDING);
    private final Function<Email, Email> setEmailStatusToSent = email -> new Email(email.getId(), email.getSubject(), email.getText(), email.getRecipients(), EmailStatus.SENT);

    @Override
    public Email save(EmailInputDTO emailDTO) {
        Email email = Email.builder()
                .subject(emailDTO.getSubject())
                .text(emailDTO.getText())
                .recipients(emailDTO.getRecipients())
                .emailStatus(EmailStatus.PENDING)
                .build();

        return emailPersistenceService.save(email);
    }

    @Override
    public List<Email> findAll() {
        return emailPersistenceService.findAll();
    }

    @Override
    public Either<Error, Email> findById(String id) {
        return emailPersistenceService.findById(id);
    }

    @Override
    public Either<Error, Email> send(String id) {
        return emailPersistenceService.findById(id)
                .filterOrElse(isEmailPending, x -> Error.SENT)
                .flatMap(emailSenderService::send)
                .map(setEmailStatusToSent)
                .map(emailPersistenceService::save);
    }

    @Override
    public List<Either<Error, Email>> sendAllPending() {
        return emailPersistenceService.findAllByEmailStatus(EmailStatus.PENDING)
                 .map(x -> emailSenderService
                                .send(x)
                                .map(setEmailStatusToSent)
                                .map(emailPersistenceService::save)
                 );

    }

}
