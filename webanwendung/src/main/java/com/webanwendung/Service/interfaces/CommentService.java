package com.webanwendung.Service.interfaces;

import java.util.List;
import java.util.Optional;

import com.webanwendung.Entitys.Comment;

import org.springframework.stereotype.Service;

@Service
public interface CommentService {

	public List<Comment> getAllComments(long id);

	public void addComment(Comment comment, long sighting_id);

	public void removeComment(Comment comment, long id);

	Optional <Comment> getById(long id);

}
