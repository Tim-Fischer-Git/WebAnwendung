package com.webanwendung.Service;

import java.io.IOException;
import java.util.List;
import com.webanwendung.Entitys.User;
import com.webanwendung.Repositorys.UserRepository;
import com.webanwendung.Service.interfaces.ImageService;
import com.webanwendung.Service.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageService imageService;

    @Override
    public User addUser(User user,MultipartFile data) {
        if (!this.exists(user.getUsername())) {
                try {
                 
                    user = userRepository.save(user);
                    imageService.avatarUpload(data.getInputStream(), user.getUsername());
                    return user;
                } catch (IOException e) {
                   
                    e.printStackTrace();
                }
                
        }
        return null;
    }
    @Override
    public User addUser(User user){
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(String id) {
        try{
            return userRepository.findByUsername(id);
        }catch(Exception e){
            return null;
        }
        
    }

    @Override
    public List<User> getAll(String sortedBy) {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, sortedBy));
    }

    @Override
    public boolean exists(String id){
        return userRepository.existsById(id);
    }

    @Override
    public List<User> filterlist(String filter) {
        return userRepository.findByusernameContainingIgnoreCase(filter);
    }

    @Override
    public List<User> logout(String username) {

        User tmpUser = userRepository.findById(username).get();
        tmpUser.setActive(false);
        userRepository.save(tmpUser);
        return this.filterlist("username");
        
    }

    @Override
    public boolean login(User user) {
        if (this.exists(user.getUsername())){
            if (this.getById(user.getUsername())
                            
                            .getPassword()
                            .equals(user.getPassword())){
                User tmpUser = userRepository.findById(user.getUsername()).get();
                tmpUser.setActive(true);
                userRepository.save(tmpUser);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        if(this.exists(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    
}