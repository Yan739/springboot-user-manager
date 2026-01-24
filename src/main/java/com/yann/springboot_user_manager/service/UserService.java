package com.yann.springboot_user_manager.service;

import com.yann.springboot_user_manager.dto.UserDTO;
import com.yann.springboot_user_manager.entity.User;
import com.yann.springboot_user_manager.mapper.UserMapper;
import com.yann.springboot_user_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository REPO;
    private final UserMapper MAPPER;

    public UserService(UserRepository repo, UserMapper mapper) {
        REPO = repo;
        MAPPER = mapper;
    }

    public boolean save(User user){
        REPO.save(user);
        return true;
    }

    public List<UserDTO> findAll(){
        List<User> users = REPO.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        int cpt = 0;

        for (User user : users){
            UserDTO dto = MAPPER.toDTO(user);
            dtos.add(dto);
            cpt++;
        }
        System.out.printf("%s Utilisateurs trouvés.",cpt);
        return dtos;
    }

    public UserDTO getUserById(long id){
        User user = REPO.findById(id).orElseThrow(()-> new RuntimeException("User not found"));

        return MAPPER.toDTO(user);
    }
}
