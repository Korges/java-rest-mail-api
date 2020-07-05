package com.korges.demo.service.persistence;

import com.korges.demo.model.dto.Error;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.ErrorEnum;
import com.korges.demo.repository.EmailRepository;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailPersistenceServiceImpl implements EmailPersistenceService {
    private final EmailRepository emailRepository;

    @Override
    public Email save(Email email) {
        log.info("[EmailPersistenceServiceImpl] - generating ResponseEntity");

        return emailRepository.save(email);
    }

    @Override
    public List<Email> findAll() {
        log.info("[EmailPersistenceServiceImpl] - generating ResponseEntity");

        return List.ofAll(emailRepository.findAll());
    }

    @Override
    public Either<Error, Email> findById(String id) {
        log.info("[EmailPersistenceServiceImpl] - fetching email by given id: " + id);

        return Option.ofOptional(emailRepository.findById(id))
                .toEither(() -> Error.build(id, ErrorEnum.NOT_FOUND));
    }

    @Override
    public List<Email> findAllByEmailStatus(EmailStatus emailStatus) {
        log.info("[EmailPersistenceServiceImpl] - fetching all by email status: " + emailStatus);

        return List.ofAll(emailRepository.findAllByEmailStatus(emailStatus));
    }

    @Override
    public Either<Error, EmailStatus> findEmailStatus(String id) {
        log.info("[EmailPersistenceServiceImpl] - fetching email status by id: " + id);

        return this.findById(id)
                .map(Email::getEmailStatus);
    }

}