package com.shotaroi.personaltodoapi.todo.dto;

import com.shotaroi.personaltodoapi.todo.TodoStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TodoCreateRequest(
        @NotBlank String title,
        String description,
        TodoStatus status,
        LocalDate dueDate
) {}
