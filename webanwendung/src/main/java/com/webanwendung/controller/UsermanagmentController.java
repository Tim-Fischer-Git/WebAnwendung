package com.webanwendung.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.webanwendung.Entitys.User;
import com.webanwendung.Service.interfaces.ImageService;
import com.webanwendung.Service.interfaces.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("user")
public class UsermanagmentController {
    private final String USER = "user";
    private final String USERTOEDIT="userToEdit";
    private final String USERLIST= "userList";
    private final String ANGEMELDETSEITE = "userlistForm";
    private final String EDITSEITE = "editForm";
    Logger logger = LoggerFactory.getLogger(UsermanagmentController.class);
    @Value("${image.safe.reposetory}")
    private String imagePath;

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    

    /**
     *  method to guarantee that Userlist is available in Model
     * @param m
     */
    @ModelAttribute(USERLIST)
    public void initUserList(Model m) {
        m.addAttribute(USERLIST, userService.getAll());
    }
    /**
     *  method to guarantee that User is available in Model
     * @param m
     */
    @ModelAttribute(USER)
    public void initUser(Model m) {
        m.addAttribute(USER, new User());
    }
    
    @GetMapping("/edit/{usernameToEdit}")
    @Transactional
    public String getEditSide(Model m, @PathVariable String usernameToEdit) {
            User userToEdit = userService.getById(usernameToEdit);
            userService.remove(usernameToEdit);
            m.addAttribute(USERLIST, userService.getAll());
            m.addAttribute(USERTOEDIT, userToEdit);
            return EDITSEITE;  
    }

    @PostMapping("/edit")
    @Transactional
    public String edit(Model m, @ModelAttribute User userToEdit,BindingResult Usererror, @ModelAttribute MultipartFile data) {
        if ( ! Usererror.hasErrors() ){
            userService.addUser(userToEdit,data);
            
            m.addAttribute(USERLIST, userService.getAll());
            return ANGEMELDETSEITE;
        }
        m.addAttribute(USERLIST, userService.getAll());
        m.addAttribute(USERTOEDIT, userToEdit);
        return EDITSEITE;
        /*
        try {

            if (!imageService.avatarUpload(data.getInputStream(), userToEdit.getUsername())) {
                m.addAttribute(USERLIST, userService.getAll());
                m.addAttribute(USERTOEDIT, userToEdit);
                return EDITSEITE;
            }else {

                userService.addUser(userToEdit);
                m.addAttribute(USERLIST, userService.getAll());
                return ANGEMELDETSEITE;
            }
        } catch (IOException e) {
            userService.addUser(userToEdit);
            m.addAttribute(USERLIST, userService.getAll());
            return ANGEMELDETSEITE;
        }*/
    }
    @GetMapping("/userlist")
    public String filterUserList(Model m) {
        m.addAttribute(USERLIST, userService.getAll());
        return ANGEMELDETSEITE;
    }

    @PostMapping("/userlist")
    public String filterUserList(Model m, @RequestParam String filter) {
        m.addAttribute(USERLIST, userService.filterlist(filter));
        return ANGEMELDETSEITE;
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable("name") String name) throws IOException {
        Path path = Paths.get(imagePath, name);
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!h1", path);
        String mimetype = Files.probeContentType(path);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok().headers(null).contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(mimetype)).body(resource);
    }



    

    
}