package com.fih.ishareing.service.resource;

import com.fih.ishareing.service.resource.model.ScaleToConfig;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    String imageScaleTo(MultipartFile image, String targetpath, Boolean usemd5name, ScaleToConfig config);
    
    void imageRemove(String image);
}