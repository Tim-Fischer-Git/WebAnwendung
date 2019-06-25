package com.webanwendung.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.webanwendung.Entitys.MetadataPacket;
import com.webanwendung.Service.interfaces.ImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.test.util.MetaAnnotationUtils;

@Service
public class ImageServiceimpl implements ImageService {
    Logger logger = LoggerFactory.getLogger(ImageServiceimpl.class);
    @Autowired
    MetadataExtractorServiceimpl metadataExtractorServiceimpl;
    @Value("${image.safe.reposetory}")
    private String path;

    @Value("${image.safe.avatarnameteil}")
    private String avatarUploadNamePart;

    @Value("${image.safe.proveimagenameteil}")
    private String proveImageNamePart;

    @Value("${image.safe.typ}")
    private String typ;

    @Override
    public Boolean avatarUpload(InputStream input, String username) {
        try {
            Path zielpfad = Paths.get(path, avatarUploadNamePart + username + typ);

            Files.copy(input, zielpfad);
            return true;
        } catch (IOException e) {
         
        }
        return false;
    }

    @Override
    public MetadataPacket proveImage(InputStream input, long id) {
        try {
            String i = Long.toString(id);
            
            Path zielpfad = Paths.get(path, proveImageNamePart + Long.toString(id) + typ);
            Files.copy(input, zielpfad);
            MetadataPacket m = metadataExtractorServiceimpl.getMetadata(path+"/"+proveImageNamePart + Long.toString(id) + typ);
            Logger logger = LoggerFactory.getLogger(CommentServiceimpl.class);
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ProveImage" + m.toString());
            return m;
        } catch (IOException e) {
          e.printStackTrace();
        }
        return new MetadataPacket();
    }

    public MetadataPacket proveImage(InputStream input, String id) {
        try {
      
            Path zielpfad = Paths.get(path, proveImageNamePart + id + typ);
            Files.copy(input, zielpfad);
            return metadataExtractorServiceimpl.getMetadata(path+"/"+proveImageNamePart + id + typ);
        } catch (IOException e) {

        }
        return new MetadataPacket();
    }



}