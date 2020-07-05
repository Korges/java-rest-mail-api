package com.korges.demo.model.dto;

import com.korges.demo.model.enums.Priority;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter @Setter
public class EmailInput {
    private String subject;
    private String text;
    private Set<String> recipients = Set.of();
    private Set<String> attachments = Set.of();
    private Priority priority = Priority.LOWEST;
}