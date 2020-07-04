package com.korges.demo.service.sender;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    // TODO
    @Override
    public Either<Error, Email> send(Email email) {
        return Either.right(email);
    }
}