package com.example.JavaToDoList.controller;

import com.example.JavaToDoList.model.Task;
import com.example.JavaToDoList.service.TaskService;
import com.example.JavaToDoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    // Получить все задачи по username
    @GetMapping("/{username}")
    public ResponseEntity<?> getTasks(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> ResponseEntity.ok(taskService.getTasksByUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Создать задачу
    @PostMapping("/{username}")
    public ResponseEntity<?> createTask(@PathVariable String username, @RequestBody Task task) {
        return userService.findByUsername(username)
                .map(user -> {
                    task.setUser(user);
                    return ResponseEntity.ok(taskService.createTask(task));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Обновить задачу
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.getTaskById(id)
                .map(existing -> {
                    existing.setTitle(updatedTask.getTitle());
                    existing.setDescription(updatedTask.getDescription());
                    existing.setCompleted(updatedTask.isCompleted());
                    existing.setDueDate(updatedTask.getDueDate());
                    return ResponseEntity.ok(taskService.updateTask(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить задачу
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
