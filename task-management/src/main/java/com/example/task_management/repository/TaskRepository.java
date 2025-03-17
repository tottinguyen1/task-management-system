package com.example.task_management.repository;

import com.example.task_management.entity.Task;
import com.example.task_management.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    // Find tasks by status with pagination and sorting
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
}
