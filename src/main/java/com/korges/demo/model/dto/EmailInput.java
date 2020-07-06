package com.korges.demo.model.dto;

import com.korges.demo.model.enums.Priority;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


@Getter @Setter @ToString
public class EmailInput {
    private String subject;
    private String text;
    private Set<String> recipients = Set.of();
    private Set<String> attachments = Set.of();
    private Priority priority = Priority.LOWEST;
}