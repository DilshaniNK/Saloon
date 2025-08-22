package com.Code.controller;


import com.Code.Repository.UserRepository;
import com.Code.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();

    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new Exception("user not found");
    }

    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User user,
                           @PathVariable("userId") Long id) throws Exception {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isEmpty()){
            throw new Exception("User Not Found");

        }
        User existingUser = otp.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);

    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new Exception("User Not Found");
        }
        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }

}
