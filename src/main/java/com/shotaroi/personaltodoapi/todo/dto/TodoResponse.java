package com.shotaroi.personaltodoapi.todo.dto;

import com.shotaroi.personaltodoapi.todo.TodoStatus;

import java.time.LocalDate;

public record TodoResponse(
        Long id,
        String title,
        String description,
        TodoStatus status,
        LocalDate dueDate
) {}
