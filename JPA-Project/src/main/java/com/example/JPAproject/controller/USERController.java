package com.example.JPAproject.controller;

import com.example.JPAproject.exception.ResourceNotFoundException;
import com.example.JPAproject.model.USER;
import com.example.JPAproject.repository.USERRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class USERController {

    @Autowired
    private USERRepository userRepository;

    @GetMapping("/users")
    public long getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).getTotalElements();
    }

    @PostMapping("/register")
    public USER registerUser(@Valid @RequestBody USER user) {
        return userRepository.save(user);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public List<USER> getUserByUsername(@PathVariable String username) {
        if(!userRepository.existsById(username)) {
            throw new ResourceNotFoundException("Username " + username + " not found!");
        }
        return userRepository.findByUsername(username);
    }
    @PutMapping("/user/{username}/update")
    public USER updateUserData(@PathVariable String username, @Valid @RequestBody USER userRequest) {
        return userRepository.findById(username)
                .map(user -> {
                    user.setName(userRequest.getName());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Username " + username + " not found"));
    }


    @DeleteMapping("/user/{username}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userRepository.findById(username)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Username " + username + " not found!"));
    }
}
