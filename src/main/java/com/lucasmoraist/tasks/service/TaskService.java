package com.lucasmoraist.tasks.service;

import com.lucasmoraist.tasks.domain.dto.CommentDTO;
import com.lucasmoraist.tasks.domain.dto.TaskRequest;
import com.lucasmoraist.tasks.domain.entity.Task;

import java.util.List;


public interface TaskService {
    void createTask(TaskRequest request);
    List<Task> listAll();
    Task findById(Long id);
    void addComment(Long id, CommentDTO dto);
    void updateTask(Long id, TaskRequest request);
    void deleteTask(Long id);
}
