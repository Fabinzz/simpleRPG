package com.fabinzz.simplerpg.controller;

import com.fabinzz.simplerpg.dto.UserDTO;
import com.fabinzz.simplerpg.exception.ResourceNotFoundException;
import com.fabinzz.simplerpg.model.User;
import com.fabinzz.simplerpg.repository.UserRepository;
import com.fabinzz.simplerpg.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> listUsers(){
        List<UserDTO> users = userService.listUsers();
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()){
            User user = userOptional.get();
            System.out.println("Usuario encontrado: " + user.getName());
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        System.out.println("Usuario não existe ou não foi possível encontrar...");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");
    }


    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO form) {
        UserDTO savedUserDTO = userService.saveUser(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
      try{
          UserDTO updatedUser = userService.updateUser(id, userDTO);
          return ResponseEntity.ok(updatedUser);
      }catch(ResourceNotFoundException exception){
          return ResponseEntity.notFound().build();
      }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        try {
           userService.deleteUser(id);
           return ResponseEntity.noContent().build();

        }catch(ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
