package com.lucasmoraist.tasks.domain.dto;

import com.lucasmoraist.tasks.domain.enums.Status;

public record TaskRequest(String title, String description, Status status) {
}
