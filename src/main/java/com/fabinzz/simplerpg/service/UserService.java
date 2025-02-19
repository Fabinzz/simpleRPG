package com.fabinzz.simplerpg.service;

import com.fabinzz.simplerpg.dto.UserDTO;
import com.fabinzz.simplerpg.exception.ResourceNotFoundException;
import com.fabinzz.simplerpg.model.User;
import com.fabinzz.simplerpg.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UserDTO updateUser( String id, UserDTO userDTO){
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()){
            User existingUser = optUser.get();
            updateUserFields(existingUser, userDTO);
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

    private void updateUserFields(User existingUser, UserDTO newUserDTO){
        if (newUserDTO.getName() != null && !newUserDTO.getName().isEmpty()){
            existingUser.setName(newUserDTO.getName());
        }
        if (newUserDTO.getEmail() != null && !newUserDTO.getEmail().isEmpty()){
            existingUser.setEmail(newUserDTO.getEmail());
        }
    }
}
