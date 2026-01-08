package com.shotaroi.personaltodoapi.todo;

import com.shotaroi.personaltodoapi.todo.dto.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public Page<TodoResponse> list(String ownerEmail, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return repo.findByOwnerEmail(ownerEmail, pageable)
                .map(t -> new TodoResponse(t.getId(), t.getTitle(), t.getDescription(), t.getStatus(), t.getDueDate()));
    }

    public TodoResponse create(String ownerEmail, TodoCreateRequest req) {
        TodoStatus status = (req.status() == null) ? TodoStatus.TODO : req.status();

        Todo todo = Todo.builder()
                .ownerEmail(ownerEmail)
                .title(req.title())
                .description(req.description())
                .status(status)
                .dueDate(req.dueDate())
                .build();

        Todo saved = repo.save(todo);
        return new TodoResponse(saved.getId(), saved.getTitle(), saved.getDescription(), saved.getStatus(), saved.getDueDate());
    }

    @Transactional
    public TodoResponse update(String ownerEmail, long id, TodoUpdateRequest req) {
        Todo todo = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        if (!todo.getOwnerEmail().equals(ownerEmail)) {
            throw new SecurityException("Forbidden");
        }

        if (req.title() != null) todo.setTitle(req.title());
        if (req.description() != null) todo.setDescription(req.description());
        if (req.status() != null) todo.setStatus(req.status());
        if (req.dueDate() != null) todo.setDueDate(req.dueDate());

        return new TodoResponse(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getStatus(), todo.getDueDate());
    }

    public void delete(String ownerEmail, long id) {
        Todo todo = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        if (!todo.getOwnerEmail().equals(ownerEmail)) {
            throw new SecurityException("Forbidden");
        }
        repo.delete(todo);
    }
}

