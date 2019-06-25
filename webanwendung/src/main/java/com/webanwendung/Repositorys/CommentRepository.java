package com.webanwendung.Repositorys;

import com.webanwendung.Entitys.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}