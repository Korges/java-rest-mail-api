package com.korges.demo.model.dto.input;


import lombok.Getter;

import java.util.Set;


@Getter
public class EmailInputDTO {
    private String subject;
    private String text;
    private final Set<String> recipients = Set.of();
    private final Set<String> attachments = Set.of();
}