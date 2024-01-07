package com.example.lab2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> index(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id ){
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/users/add")
    public void addUser(@RequestBody User user){
        userRepository.save(user);
    }

    @PutMapping("/user/{id}/update")
    public void updateUser(@PathVariable("id") Integer id, @RequestBody User user){
        if(userRepository.existsById(id)){
            User userToUpdate = userRepository.findById(id).orElse(null);
            userToUpdate = user;
            userToUpdate.setId(id);
            userRepository.save(userToUpdate);
        }

    }

    @DeleteMapping("/user/{id}/delete")
    public void deleteUser(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
    }
}
