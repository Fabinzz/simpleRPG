package com.fabinzz.simplerpg.service;

import com.fabinzz.simplerpg.dto.UserDTO;
import com.fabinzz.simplerpg.exception.ResourceNotFoundException;
import com.fabinzz.simplerpg.model.User;
import com.fabinzz.simplerpg.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserDTO> listUsers(){
        List<User> users = userRepository.findAll();
        return UserDTO.converter(users);
    }

    public Optional<User> findById( String id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return Optional.empty();
        }
        return user;
    }

    @Transactional
    public UserDTO saveUser(UserDTO form){
        if(form.getName()==null || form.getEmail()==null){
            throw new IllegalArgumentException("Nome e email são obrigatórios.");
        }
        User user = form.converter();
        userRepository.save(user);
        System.out.println("Usuário "+ user.getName() + " foi salvo com sucesso!");
        System.out.println("Email: "+ user.getEmail());
        System.out.println("ID: "+ user.getId());
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO updateUser( String id, User user){
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()){
            User existingUser = optUser.get();
            updateUserFields(existingUser, user);
            userRepository.save(existingUser);

            System.out.println(id + " foi atualizado com sucesso!");
            return new UserDTO(existingUser);
        }else {
            System.out.println("Falha ao atualizar: usuário não encontrado.");
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }
    }

    @Transactional
    public void deleteUser( String id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            userRepository.deleteById(id);
            System.out.println(id + " foi deletado com sucesso!");
        }else{
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }
    }

    private void updateUserFields(User existingUser, User newUser){
        if (newUser.getName() != null && !newUser.getName().isEmpty()){
            existingUser.setName(newUser.getName());
        }
        if (newUser.getEmail() != null && !newUser.getEmail().isEmpty()){
            existingUser.setEmail(newUser.getEmail());
        }
    }
}
