package com.lucasmoraist.tasks.service.impl;

import com.lucasmoraist.tasks.domain.dto.CommentDTO;
import com.lucasmoraist.tasks.domain.dto.TaskRequest;
import com.lucasmoraist.tasks.domain.entity.Task;
import com.lucasmoraist.tasks.domain.model.Comment;
import com.lucasmoraist.tasks.repository.TaskRepository;
import com.lucasmoraist.tasks.repository.http.CommentClient;
import com.lucasmoraist.tasks.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final CommentClient commentClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void createTask(TaskRequest request) {
        if (request.title().isEmpty()) {
            log.error("Error creating task: title cannot be empty");
            throw new IllegalArgumentException("Title cannot be empty");
        }

        log.info("Creating new task with title: {}", request.title());

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .build();

        this.repository.save(task);
    }

    @Transactional
    @Override
    public List<Task> listAll() {
        log.info("Listing all tasks");
        List<Comment> comments = this.commentClient.listAll();
        return this.repository
                .findAll()
                .stream().peek(t -> t.setComment(comments
                        .stream()
                        .filter(c -> c.getTaskId().equals(t.getId()))
                        .toList())).toList()
                ;
    }

    @Override
    public Task findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Task not found with id: {}", id);
                    return new IllegalArgumentException("Task not found");
                });
    }

    @Transactional
    @Override
    public void addComment(Long id, CommentDTO dto) {

        CommentDTO comment = new CommentDTO(dto.text(), id);

        this.rabbitTemplate.convertAndSend(
                "task.ex",
                "",
                comment);

        Task task = this.findById(id);
        task.getComment().add(Comment.builder()
                .text(comment.text())
                .taskId(id)
                .build());

        this.repository.save(task);
    }

    @Override
    public void updateTask(Long id, TaskRequest request) {
        log.info("Updating task with id: {}", id);
        Task task = this.findById(id);

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());

        this.repository.save(task);
        log.info("Task updated successfully");
    }

    @Override
    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);
        Task task = this.findById(id);
        this.repository.delete(task);
        log.info("Task deleted successfully");
    }
}
