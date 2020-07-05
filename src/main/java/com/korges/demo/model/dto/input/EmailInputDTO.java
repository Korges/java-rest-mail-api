package com.korges.demo.model.dto.input;


import lombok.Getter;

import java.util.List;


@Getter
public class EmailInputDTO {
    private String subject;
    private String text;
    private final List<String> recipients = List.of();
}