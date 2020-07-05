package com.korges.demo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Priority {
    HIGHEST(1),
    HIGH(2),
    MIDDLE(3),
    LOW(4),
    LOWEST(5);

    private final int value;

}