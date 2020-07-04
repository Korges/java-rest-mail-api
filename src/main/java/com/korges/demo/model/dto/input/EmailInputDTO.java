package com.korges.demo.model.dto.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class EmailInputDTO {
    private final String header;
    private final String message;
    private final List<String> recipients;
}