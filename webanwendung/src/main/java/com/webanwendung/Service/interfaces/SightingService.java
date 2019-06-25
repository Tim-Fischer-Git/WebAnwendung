package com.webanwendung.Service.interfaces;

import java.util.List;

import com.webanwendung.Entitys.Sighting;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface SightingService {
    Sighting addSighting(Sighting sighting,MultipartFile data);

    List <Sighting> getAll();

    Sighting getById(long id);
    boolean exists(long id);
    boolean remove(long id);


}
