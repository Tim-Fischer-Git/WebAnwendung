package com.webanwendung.Service.interfaces;

import java.util.List;

import com.webanwendung.Entitys.User;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    User addUser(User user,MultipartFile data);
    List<User> logout(String username);
    boolean login(User user);
    List<User> getAll();
    List<User> getAll(String sortedBy);
    User getById(String username);
    boolean exists(String id);
    boolean remove(String id);
    List<User> filterlist(String filter);
    User addUser(User user);

}