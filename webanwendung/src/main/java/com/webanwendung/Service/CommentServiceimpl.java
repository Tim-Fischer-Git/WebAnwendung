package com.webanwendung.Service;

import java.util.List;
import java.util.Optional;

import com.webanwendung.Entitys.Comment;
import com.webanwendung.Entitys.Sighting;
import com.webanwendung.Repositorys.CommentRepository;
import com.webanwendung.Service.interfaces.CommentService;
import com.webanwendung.Service.interfaces.SightingService;
import com.webanwendung.Service.interfaces.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceimpl implements CommentService {
   @Autowired
   SightingService sightingService;
   
   Logger logger = LoggerFactory.getLogger(CommentServiceimpl.class);
   @Autowired
   CommentRepository commentRepository;

   @Autowired
   UserService userService;

   @Override
   public List<Comment> getAllComments(long id) {
      return sightingService.getById(id).getCommentList();
   }

   @Override
   @Transactional
   public void addComment(Comment comment, long sighting_id) {
      Sighting s = sightingService.getById(sighting_id);
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails) {

         comment.setAuthor(userService.getById ( ((UserDetails)principal).getUsername()));
   
         comment =commentRepository.save(comment);
         s.addComment(comment);
      }
   }

   @Override
   @Transactional
   public void removeComment(Comment comment, long id) {
      sightingService.getById(id).removeComment(comment);
   }

   @Override
   public Optional<Comment> getById(long id) {
      return commentRepository.findById(id);
   }

   



   
}