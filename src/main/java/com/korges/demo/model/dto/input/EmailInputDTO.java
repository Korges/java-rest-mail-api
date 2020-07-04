package com.korges.demo.model.dto.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class EmailInputDTO {
    private final String subject;
    private final String text;
    private final List<String> recipients;
}