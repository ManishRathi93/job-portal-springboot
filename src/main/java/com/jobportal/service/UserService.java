package com.jobportal.service;

import com.jobportal.entity.User;
import com.jobportal.enums.UserRole;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public List<User> getUsersByRole(UserRole role){
        return userRepository.findByRole(role);
    }

    public List<User> getAllJobSeekers(){
        return userRepository.findByRole(UserRole.JOB_SEEKER);
    }

    public List<User> getAllEmployers(){
        return userRepository.findByRole(UserRole.EMPLOYER);
    }

    public List<User> searchByUserName(String name){
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public void deleteUsers(Long id){
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser){
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setAbout(updatedUser.getAbout());
            user.setResumeUrl(updatedUser.getResumeUrl());
            user.setCompanyName(updatedUser.getCompanyName());
            user.setCompanyDescription(updatedUser.getCompanyDescription());
            return userRepository.save(user);
        }
        return null;
    }
}
