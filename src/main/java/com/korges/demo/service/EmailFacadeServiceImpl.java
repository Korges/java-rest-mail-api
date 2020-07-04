package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Error;
import com.korges.demo.service.persistence.EmailPersistenceService;
import com.korges.demo.service.sender.EmailSenderService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class EmailFacadeServiceImpl implements EmailFacadeService {
    private final EmailPersistenceService emailPersistenceService;
    private final EmailSenderService emailSenderService;

    private final Predicate<Email> isEmailPending =  email -> email.getStatus().equals(EmailStatus.PENDING);
    private final Function<Email, Email> setEmailStatusToSent = email -> new Email(email.getId(), email.getSubject(), email.getText(), email.getRecipients(), EmailStatus.SENT);

    @Override
    public Email save(EmailInputDTO emailDTO) {
        Email email = Email.builder()
                .subject(emailDTO.getSubject())
                .text(emailDTO.getText())
                .recipients(isNull(emailDTO.getRecipients()) ? List.of() : emailDTO.getRecipients())
                .status(EmailStatus.PENDING)
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

}
