package com.korges.demo.model.dto.input;

import com.korges.demo.model.enums.Priority;
import lombok.Getter;

import java.util.Set;


@Getter
public class EmailInputDTO {
    private String subject;
    private String text;
    private Set<String> recipients = Set.of();
    private Set<String> attachments = Set.of();
    private Priority priority = Priority.LOWEST;
}