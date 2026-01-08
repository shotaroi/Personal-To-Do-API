package com.shotaroi.personaltodoapi.todo;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByOwnerEmail(String ownerEmail, Pageable pageable);
}
