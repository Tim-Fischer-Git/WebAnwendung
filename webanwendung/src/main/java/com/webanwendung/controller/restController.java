package com.webanwendung.controller;

import java.util.ArrayList;
import java.util.List;

import com.webanwendung.Entitys.Comment;
import com.webanwendung.Entitys.Sighting;
import com.webanwendung.Service.interfaces.CommentService;
import com.webanwendung.Service.interfaces.SightingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {

    @Autowired
    SightingService sightingService;

    @Autowired 
    CommentService commentService;
    
    @RequestMapping(value = "/rest/sighting/{sid}", method = RequestMethod.GET)
    public Sighting getSighting(@PathVariable ("sid") Long sid){
        return sightingService.getById(sid);
    }

    // SightingS s nicht vergessen
    @RequestMapping(value = "/rest/sightings", method = RequestMethod.GET)
    public ArrayList <String> getSightings(){
        ArrayList <String> UriList = new ArrayList <String> ();
        for(int i = 0; i < sightingService.getAll().size() ;i++){
                UriList.add("http://localhost:5634/rest/sighting/" + sightingService.getAll().get(i).getId());
            }  
        return UriList;
    }

    @RequestMapping(value = "/rest/sighting/{sid}/comments", method = RequestMethod.GET)
    public List<Comment> getAllCommentsOfSighting(@PathVariable ("sid") Long sid) {
        return sightingService.getById(sid).getCommentList();
    } 

    @RequestMapping(value = "/rest/sighting/{sid}/comment/{kid}", method = RequestMethod.GET)
    public Comment getCommentOfSighting(@PathVariable ("kid") Long kid) {
       // return sightingService.getById(sid).get().getCommentList().getComment(  Hier dann per kid suchen...   )
       // Hier kann nicht auf die Sichtung zugegriffen werden. Es wuerde sonst die CommentLIST zurueck gegeben werden. 
       // Man muesste sagen dass ID von der entity comment == index in der list, macht aber keinen sinn eig.
       return commentService.getById(kid).get();
    }   


    @RequestMapping(value = "/rest/sighting/{sid}/comment/{kid} ", method = RequestMethod.DELETE)
    public boolean postDeleteCommentOfSighting(@PathVariable ("sid") Long sid,@PathVariable ("sid") Long kid, @RequestBody Comment comment) {
        // Jackson sorgt dafuer, dass Requestbody die attribute mapped und dann die requestmethod applied
        return true;
    } 

    @RequestMapping(value = "/rest/sighting/{sid}/comment/{kid} ", method = RequestMethod.POST)
    public Comment postAddCommentOfSighting(@PathVariable ("sid") Long sid,@PathVariable ("sid") Long kid, @RequestBody Comment comment) {
    
        return comment;
    } 

    @RequestMapping(value = "/rest/sighting/{sid}/comment/{kid} ", method = RequestMethod.PUT)
    public boolean postChangeCommentOfSighting(@PathVariable ("sid") Long sid,@PathVariable ("sid") Long kid, @RequestBody Comment comment) {
    
        return true;
    } 


}