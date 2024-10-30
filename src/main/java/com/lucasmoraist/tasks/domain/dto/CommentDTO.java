package com.lucasmoraist.tasks.domain.dto;

public record CommentDTO(
        String text,
        Long taskId
) {
}
