package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.List;
import java.util.Optional;

public interface EmailFacadeService {

    Email save(EmailInputDTO email);
    List<Email> findAll();
    Either<Error, Email> findById(String id);
    Either<Error, Email> send(String id);

}
