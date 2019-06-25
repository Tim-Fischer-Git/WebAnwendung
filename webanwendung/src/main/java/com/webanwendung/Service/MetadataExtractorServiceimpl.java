package com.webanwendung.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import com.webanwendung.Entitys.MetadataPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MetadataExtractorServiceimpl {

   public MetadataPacket getMetadata(String filePath) {
      Logger logger = LoggerFactory.getLogger(CommentServiceimpl.class);

      Metadata metadata;
      MetadataPacket result = new MetadataPacket();
      try {
        
         logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + filePath);
         metadata = ImageMetadataReader.readMetadata(new FileInputStream(filePath));
         logger.info("" + metadata.toString());
         Directory exififd0dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
         logger.info("" + exififd0dir.toString());

         Date origdate = exififd0dir.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);
         LocalDate currentDate;
         if (origdate != null){
             currentDate =LocalDate.of(origdate.getYear(),origdate.getMonth(),origdate.getDay());
         }else{
             currentDate = null;
         }
         
         
         
         GpsDirectory geodir = metadata.getFirstDirectoryOfType(GpsDirectory.class);
         GeoLocation geoloc = geodir.getGeoLocation();
      
         result.setDate(currentDate);
         result.setDegreeOfLongitude(geoloc.getLongitude());
         result.setDegreeOfLatitude(geoloc.getLatitude());
         
      } catch (ImageProcessingException | IOException e) {
         e.printStackTrace();
         
      }
     

       return result;
   }
}