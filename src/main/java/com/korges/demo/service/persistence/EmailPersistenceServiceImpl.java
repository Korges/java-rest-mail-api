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
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailPersistenceServiceImpl implements EmailPersistenceService {
    private final EmailRepository emailRepository;

    @Override
    public Email save(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public List<Email> findAll() {
        return List.ofAll(emailRepository.findAll());
    }

    @Override
    public Either<Error, Email> findById(String id) {
        return Option.ofOptional(emailRepository.findById(id))
                .toEither(() -> Error.build(id, ErrorEnum.NOT_FOUND));
    }

    @Override
    public List<Email> findAllByEmailStatus(EmailStatus emailStatus) {
        return List.ofAll(emailRepository.findAllByEmailStatus(emailStatus));
    }

    @Override
    public Either<Error, EmailStatus> findEmailStatus(String id) {
        return this.findById(id)
                .map(Email::getEmailStatus);
    }

}