package com.webanwendung.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import com.webanwendung.Entitys.Comment;
import com.webanwendung.Entitys.Sighting;
import com.webanwendung.Entitys.User;
import com.webanwendung.Service.interfaces.CommentService;
import com.webanwendung.Service.interfaces.ImageService;
import com.webanwendung.Service.interfaces.SightingService;
import com.webanwendung.Service.interfaces.UserService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(names = { "sightings", "language" })

public class SightingController {
    private final String SIGHTINGSLIST = "sightings";
    private final String SIGHTING = "sighting";
    private final String SIGHTINGPATH = "/sighting";
    private final String COMMENT = "comment";
    private final String LANGUAGE = "language";
    private final String SIGHTINGFORM = "sightingForm";
    private final String COMMENTLIST = "commentlist";
    private final String DETAILSIGHT = "sightingDetailForm";

    // @Value("${image.safe.reposetory}")
    private String imagePath = "/tmp";
    Logger logger = LoggerFactory.getLogger(SightingController.class);
    @Autowired
    ImageService imageService;

    @Autowired
    SightingService sightingService;

    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    /**
     * method to guarantee that Comment is available in Model
     */
    @ModelAttribute(COMMENT)
    public void initComment(Model m) {
        m.addAttribute(COMMENT, new Comment());
    }

    /**
     * method to guarantee that sightinglist is available in Model
     */
    @ModelAttribute(SIGHTINGSLIST)
    public void initSightingsList(Model m) {
        m.addAttribute(SIGHTINGSLIST, sightingService.getAll());
    }

    /**
     * method to guarantee that language is available in Model
     * 
     * @param m = Model
     */
    @ModelAttribute(LANGUAGE)
    public void initLanguage(Model m) {
        m.addAttribute(LANGUAGE, "de");
    }

    /**
     * method to guarantee that sighting is available in Model
     * 
     * @param m = Model
     */
    @ModelAttribute(SIGHTING)
    public void initSighting(Model m) {
        m.addAttribute(SIGHTING, new Sighting());
    }

    /**
     * show a Blank sighting side
     * 
     * @param m = Model
     */
    @GetMapping(SIGHTINGPATH)
    public String showBlankForm(Model m) {
      
       
        return SIGHTINGFORM;
    }

    @GetMapping(SIGHTINGPATH + "/{id}")
    public String editSighting(Model m, @PathVariable long id) {
        m.addAttribute(SIGHTING, sightingService.getById(id));
        sightingService.remove(id);
        m.addAttribute(SIGHTINGSLIST, sightingService.getAll());
        return SIGHTINGFORM;
    }

    @PostMapping(SIGHTINGPATH)
    public String addSighting(@Valid @ModelAttribute Sighting sighting, 
            BindingResult sightingErrors, Model m,
            @ModelAttribute MultipartFile data,@ModelAttribute User user){
                
        if ( ! sightingErrors.hasErrors() ){
            sighting =sightingService.addSighting(sighting,data);
            logger.info("!!!!!!!!!!!!!!!!!!!!!sighting"+sighting.getMetadataPacket().toString());
            m.addAttribute(SIGHTING,new Sighting());
            m.addAttribute(SIGHTINGSLIST, sightingService.getAll());
            return SIGHTINGFORM;
        }

        m.addAttribute(SIGHTING, sighting);
        m.addAttribute(SIGHTINGSLIST, sightingService.getAll());
        
        return SIGHTINGFORM;
    }

    @GetMapping(SIGHTINGPATH + "/image/{name}")// muss ich noch auslagern
    public ResponseEntity<Resource> getImage(@PathVariable("name") String name) {
        Path path = Paths.get(imagePath, name);
        String mimetype;
        try {
            mimetype = Files.probeContentType(path);
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok().headers(null).contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(mimetype)).body(resource);
        } catch (IOException e) {
            path = Paths.get(imagePath, "default.png");
            try {
                mimetype = Files.probeContentType(path);
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                return ResponseEntity.ok().headers(null).contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(mimetype)).body(resource);
            } catch (IOException e1) {
           
                e1.printStackTrace();
            }
            
            e.printStackTrace();
        }
        return null;
       
    }
    
    @GetMapping(SIGHTINGPATH + "/detail/{id}")
    public String getDetailSide(Model m, @PathVariable long id) {
        m.addAttribute(COMMENT, new Comment());
        m.addAttribute(SIGHTING, sightingService.getById(id));
        m.addAttribute(SIGHTINGSLIST, sightingService.getAll());
        m.addAttribute(COMMENTLIST, commentService.getAllComments(id));
        return DETAILSIGHT;
    }

    @PostMapping(SIGHTINGPATH + "/detail/{id}/add")
    public String addComment(Model m, @PathVariable long id,@ModelAttribute Comment comment) {
        commentService.addComment(comment, id);
        m.addAttribute(COMMENT, new Comment());
        m.addAttribute(SIGHTING, sightingService.getById(id));
        m.addAttribute(SIGHTINGSLIST, sightingService.getAll());
        m.addAttribute(COMMENTLIST, commentService.getAllComments(id));
        return DETAILSIGHT;
    }
    
    @PostMapping(SIGHTINGPATH + "/detail/{id}/remove")
    public String removeComment(Model m, @PathVariable long id,@ModelAttribute Comment comment) {
        commentService.removeComment(comment, id);
        m.addAttribute(COMMENT, new Comment());
        m.addAttribute(SIGHTING, sightingService.getById(id));
        m.addAttribute(SIGHTINGSLIST, sightingService.getAll());
        m.addAttribute(COMMENTLIST, commentService.getAllComments(id));
        return DETAILSIGHT;
    }
    @GetMapping(SIGHTINGPATH + "/detail/{sid}/{cid}")
    public String editComment(Model m, @PathVariable Long sid ,@PathVariable Long cid ){
        Comment comment = commentService.getById(cid).get();
        m.addAttribute(COMMENT, comment);
        m.addAttribute(SIGHTING, sightingService.getById(sid));
        commentService.removeComment(comment, sid);
        m.addAttribute(COMMENTLIST, commentService.getAllComments(sid));
        return DETAILSIGHT;
    }
    

}
