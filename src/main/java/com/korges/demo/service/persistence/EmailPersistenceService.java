package com.korges.demo.service.persistence;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Error;
import io.vavr.collection.List;
import io.vavr.control.Either;


public interface EmailPersistenceService {

    Email save(Email email);
    List<Email> findAll();
    Either<Error, Email> findById(String id);
    List<Email> findAllByEmailStatus(EmailStatus status);

}