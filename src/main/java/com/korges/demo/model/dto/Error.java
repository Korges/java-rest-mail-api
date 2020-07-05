package com.korges.demo.model.dto;

import com.korges.demo.model.enums.ErrorEnum;
import lombok.Getter;

@Getter
public class Error {

    private final String id;
    private final ErrorEnum error;
    private final String message;

    private Error(String id, ErrorEnum error) {
        this.id = id;
        this.error = error;
        this.message = error.getMessage();
    }

    public static Error build(String id, ErrorEnum errorEnum) {
        return new Error(id, errorEnum);
    }

}
