package com.fabinzz.simplerpg.controller;

import com.fabinzz.simplerpg.dto.UserDTO;
import com.fabinzz.simplerpg.dto.UserFormDTO;
import com.fabinzz.simplerpg.model.User;
import com.fabinzz.simplerpg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserDTO> listUsers(){
        List<User> users = userRepository.findAll();
        return UserDTO.converter(users);
    }
    @Transactional
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Transactional
    @PostMapping
    public UserDTO saveUser(@RequestBody UserFormDTO form){
        User user = form.converter();
        userRepository.save(user);
        System.out.println("Usuário "+ user.getName() + " foi salvo com sucesso!");
        System.out.println("Email: "+ user.getEmail());
        System.out.println("ID: "+ user.getId());

        return new UserDTO(user);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody User user){
        final Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User existingUser = optUser.get();
            if (user.getName() != null && !user.getName().isEmpty()){
                existingUser.setName(user.getName());
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty()){
                existingUser.setEmail(user.getEmail());
            }
            userRepository.save(existingUser);
            System.out.println(id + " foi atualizado com sucesso!");
            return ResponseEntity.ok(new UserDTO(existingUser));
        }else {
            System.out.println("Falha ao atualizar!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        final Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            System.out.println(id + " foi deletado com sucesso!");
            userRepository.deleteById(id);
        }
    }
}
