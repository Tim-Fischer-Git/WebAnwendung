package com.webanwendung.Repositorys;

import java.util.List;

import com.webanwendung.Entitys.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByusernameContainingIgnoreCase(String username);
    User findByUsername(String username);

}