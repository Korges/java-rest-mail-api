package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import io.vavr.collection.List;
import io.vavr.control.Either;

public interface EmailFacadeService {

    List<Email> findAll();
    Either<Error, Email> findById(String id);
    Email save(EmailInputDTO email);
    Either<Error, Email> update(String id, EmailInputDTO email);
    Either<Error, Email> send(String id);
    List<Either<Error, Email>> sendAllPending();

}