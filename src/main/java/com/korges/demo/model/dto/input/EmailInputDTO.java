package com.korges.demo.model.dto.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailInputDTO {
    private final String header;
    private final String message;
}