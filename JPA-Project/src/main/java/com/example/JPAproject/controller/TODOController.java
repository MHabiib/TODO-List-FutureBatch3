package com.example.JPAproject.controller;

import com.example.JPAproject.exception.ResourceNotFoundException;
import com.example.JPAproject.model.TODO;
import com.example.JPAproject.repository.TODORepository;
import com.example.JPAproject.repository.USERRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class TODOController {

    @Autowired
    private TODORepository todoRepository;

    @Autowired
    private USERRepository userRepository;

    @GetMapping("/todos")
    public long getTodos(Pageable pageable) {
        return todoRepository.findAll(pageable).getTotalElements();
    }

    @RequestMapping(value = "/user/{username}/todo", method = RequestMethod.GET)
    public List<TODO> getTodosByUsername(@PathVariable String username) {
         if(!userRepository.existsById(username)) {
            throw new ResourceNotFoundException("Username " + username + " not found!");
        }
        return todoRepository.findAllByUser_Username(username);
    }

    @PostMapping("/user/{username}/todo/new")
    public TODO createTodo(@PathVariable String  username, @Valid @RequestBody TODO todo) {
        return userRepository.findById(username)
                .map(user -> {
                    todo.setUser(user);
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("Username " + username + " not found!"));
    }

    @PutMapping("user/{username}/todo/{todo_id}/update")
    public TODO updateTodoContent(@PathVariable String username,
                                  @PathVariable Long todo_id,
                                  @Valid @RequestBody TODO todoRequest) {
        if (!userRepository.existsById(username)) {
            throw new ResourceNotFoundException("Username " + username + " not found!");
        }

        return todoRepository.findById(todo_id)
                .map(TODO -> {
                    TODO.setTitle(todoRequest.getTitle());
                    TODO.setDescription(todoRequest.getDescription());
                    TODO.setStatus(todoRequest.getStatus());
                    return todoRepository.save(TODO);
                }).orElseThrow(() -> new ResourceNotFoundException("Todo with id " + todo_id + " not found!"));
    }

    @DeleteMapping("/user/{username}/todo/{todo_id}/delete")
    public ResponseEntity<?> deleteTodo(@PathVariable String username,
                                        @PathVariable Long todo_id) {
        if(!userRepository.existsById(username)) {
            throw new ResourceNotFoundException("Username " + username + " not found!");
        }

        return todoRepository.findById(todo_id)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todo with id " + todo_id + " not found!" ));
    }
}
