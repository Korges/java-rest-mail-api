package com.korges.demo.service.persistence;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Error;
import com.korges.demo.repository.EmailRepository;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return emailRepository.findAll();
    }

    @Override
    public Either<Error, Email> findById(String id) {
        return Option.ofOptional(emailRepository.findById(id))
                .toEither(() -> Error.NOT_FOUND);
    }

    @Override
    public List<Email> findAllByEmailStatus(EmailStatus emailStatus) {
        return this.emailRepository.findAllByEmailStatus(emailStatus);
    }

}