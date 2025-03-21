package com.example.task_management.service;

import com.example.task_management.entity.Task;
import com.example.task_management.entity.TaskStatus;
import com.example.task_management.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Fetch all tasks from the database.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Find a specific task by its ID.
     */
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    /**
     * Add a new task to the database.
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Update an existing task based on its ID.
     */
    public Task updateTask(String id, Task taskDetails) {
        // Find the task, or throw an error if it doesn't exist
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        // Update the task details
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        return taskRepository.save(task);
    }

    /**
     * Delete a task from the database using its ID.
     */
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    /**
     * Get a paginated and sorted list of all tasks.
     */
    public Page<Task> getAllTasks(int page, int size, String sortBy, String sortDir) {
        Sort sort = getSortOrder(sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(pageable);
    }

    /**
     * Get tasks filtered by status, with pagination and sorting.
     */
    public Page<Task> getTasksByStatus(TaskStatus status, int page, int size, String sortBy, String sortDir) {
        Sort sort = getSortOrder(sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findByStatus(status, pageable);
    }

    /**
     * Get tasks filtered by title, with pagination and sorting.
     */
    public Page<Task> getTasksByTitle(String title, int page, int size, String sortBy, String sortDir) {
        Sort sort = getSortOrder(sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findByTitle(title, pageable);
    }

    public Page<Task> getTasksByTitleStartingWith(String title, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findByTitleStartingWith(title, pageable);
    }

    /**
     * Helper method to determine the sorting order (ascending or descending).
     */
    private Sort getSortOrder(String sortBy, String sortDir) {
        return sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    }
}

