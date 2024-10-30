package com.lucasmoraist.tasks.controller;

import com.lucasmoraist.tasks.domain.dto.CommentDTO;
import com.lucasmoraist.tasks.domain.dto.TaskRequest;
import com.lucasmoraist.tasks.domain.entity.Task;
import com.lucasmoraist.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        log.info("Getting all tasks");
        List<Task> tasks = this.service.listAll();
        log.info("Tasks retrieved successfully: {}", tasks);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        log.info("Getting task with id {}", id);
        Task task = this.service.findById(id);
        log.info("Task retrieved successfully: {}", task);
        return ResponseEntity.ok().body(task);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TaskRequest request) {
        log.info("Creating task with data: {}", request);
        this.service.createTask(request);
        log.info("Task created successfully");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @Valid @RequestBody CommentDTO request) {
        log.info("Adding comment to task with id {} and data: {}", id, request);
        this.service.addComment(id, request);
        log.info("Comment added to task with id {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        log.info("Updating task with id {} and data: {}", id, request);
        this.service.updateTask(id, request);
        log.info("Task with id {} updated successfully", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting task with id {}", id);
        this.service.deleteTask(id);
        log.info("Task with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }




}
