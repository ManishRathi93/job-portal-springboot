package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService service;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(service.emailExists(user.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User savedUser = service.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user = service.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        Optional<User> user = service.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/job-seekers")
    public ResponseEntity<List<User>> getAllJobSeekers(){
        List<User> jobSeekers = service.getAllJobSeekers();
        return ResponseEntity.ok(jobSeekers);
    }

    @GetMapping("/employers")
    public ResponseEntity<List<User>> getAllEmployers(){
        List<User> employers = service.getAllEmployers();
        return ResponseEntity.ok(employers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name){
        List<User> users = service.searchByUserName(name);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User updateUser = service.updateUser(id,user);
        if(updateUser != null){
            return ResponseEntity.ok(updateUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        Optional<User> user = service.getUserById(id);
        if(user.isPresent()){
            service.deleteUsers(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
