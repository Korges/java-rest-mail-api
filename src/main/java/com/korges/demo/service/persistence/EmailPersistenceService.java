package com.korges.demo.service.persistence;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import io.vavr.control.Either;

import java.util.List;

public interface EmailPersistenceService {

    Email save(Email email);
    List<Email> findAll();
    Either<Error, Email> findById(String id);

}