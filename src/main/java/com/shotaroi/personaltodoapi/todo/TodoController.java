package com.shotaroi.personaltodoapi.todo;

import com.shotaroi.personaltodoapi.todo.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public Page<TodoResponse> list(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String email = (String) auth.getPrincipal();
        return service.list(email, page, size);
    }

    @PostMapping
    public TodoResponse create(Authentication auth, @Valid @RequestBody TodoCreateRequest req) {
        String email = (String) auth.getPrincipal();
        return service.create(email, req);
    }

    @PatchMapping("/{id}")
    public TodoResponse update(Authentication auth, @PathVariable long id, @RequestBody TodoUpdateRequest req) {
        String email = (String) auth.getPrincipal();
        return service.update(email, id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication auth, @PathVariable long id) {
        String email = (String) auth.getPrincipal();
        service.delete(email, id);
    }
}

