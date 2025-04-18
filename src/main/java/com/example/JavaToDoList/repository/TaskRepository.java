package com.example.JavaToDoList.repository;

import com.example.JavaToDoList.model.Task;
import com.example.JavaToDoList.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
