package com.korges.demo.service.sender;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import io.vavr.control.Either;

public interface EmailSenderService {

    Either<Error, Email> send(Email email);

}