package com.korges.demo.service.sender;

import com.korges.demo.model.dto.input.Error;
import com.korges.demo.model.entity.Email;
import io.vavr.control.Either;

public interface EmailSenderService {

    Either<Error, Email> send(Email email);

}