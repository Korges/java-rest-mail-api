package com.korges.demo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {
    NOT_FOUND("Email not found by given id", 404),
    SENT("Email has been already sent", 400),
    NO_RECIPIENTS("Email has no defined recipients", 400),
    UNSPECIFIED("", 500);

    private final String message;
    private final int code;

}