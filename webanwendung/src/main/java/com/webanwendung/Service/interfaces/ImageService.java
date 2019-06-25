package com.webanwendung.Service.interfaces;

import java.io.InputStream;

import com.webanwendung.Entitys.MetadataPacket;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    Boolean avatarUpload (InputStream input,String Username);
    MetadataPacket proveImage(InputStream input,long id);
} 

