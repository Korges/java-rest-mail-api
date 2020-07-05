package com.korges.demo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum Error {
    NOT_FOUND("Email not found by given id", HttpStatus.NOT_FOUND),
    SENT("Email has been already sent", HttpStatus.BAD_REQUEST),
    NO_RECIPIENTS("Email has no defined recipients", HttpStatus.BAD_REQUEST),
    MAIL_SENDER_ERROR("MailSenderService Exception. Please check logs.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNSPECIFIED("", HttpStatus.NOT_IMPLEMENTED);

    private final String message;
    private final HttpStatus code;

}