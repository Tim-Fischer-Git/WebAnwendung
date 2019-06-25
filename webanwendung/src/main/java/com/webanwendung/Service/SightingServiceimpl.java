package com.webanwendung.Service;

import java.io.IOException;
import java.util.List;

import com.webanwendung.Entitys.MetadataPacket;
import com.webanwendung.Entitys.Sighting;
import com.webanwendung.Repositorys.MetadataPacketRepository;
import com.webanwendung.Repositorys.SightingRepository;
import com.webanwendung.Service.interfaces.ImageService;
import com.webanwendung.Service.interfaces.SightingService;
import com.webanwendung.Service.interfaces.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SightingServiceimpl implements SightingService {
    @Autowired
    SightingRepository sightingRepository;
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;
    @Autowired
    MetadataPacketRepository metadataPacketRepository;
    @Override
    public Sighting addSighting(Sighting sighting, MultipartFile data) {
        if (!this.exists(sighting.getId())) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {

                MetadataPacket metadataPacket;
                try {
                    sighting.setAuthor(userService.getById(((UserDetails) principal).getUsername()));
                     
                    sighting = sightingRepository.save(sighting);
                    metadataPacket =imageService.proveImage(data.getInputStream(), sighting.getId());
                    Logger logger = LoggerFactory.getLogger(CommentServiceimpl.class);
                    logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!addSighting" + metadataPacket.toString());
                    
                    if(metadataPacket.getDate() != null){
                      sighting.setDate(metadataPacket.getDate());
                    }
                    /*
                    if (metadataPacket.getDegreeOfLatitude() == null || metadataPacket.getDegreeOfLongitude() == null){
                        metadataPacket.setDegreeOfLatitude(0);
                        metadataPacket.setDegreeOfLongitude(0);
                    }
                    */
                    metadataPacket =metadataPacketRepository.save(metadataPacket);
                    sighting.setMetadataPacket(metadataPacket);
                 
                    sighting = sightingRepository.save(sighting);
                    
                    return sighting;
                } catch (IOException e) {
                   
                    e.printStackTrace();
                }
                
            }
        }
        return null;
    }

    @Override
    public List<Sighting> getAll() {
        return sightingRepository.findAll();
        
    }

    @Override
    public Sighting getById(long id) {
        return sightingRepository.findById(id);
    }

    @Override
    public boolean remove(long id) {
        if (this.exists(id)) {
            sightingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean exists(long id) {
        return sightingRepository.existsById(id);
    }

 

   
    
    
}