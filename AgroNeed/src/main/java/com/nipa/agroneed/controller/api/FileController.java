package com.nipa.agroneed.controller.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
                RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class FileController {
    @Value("${file.upload-dir}")
    private String imageStoreLocation;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        File file = new File(imageStoreLocation + "/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();  // Return 404 if image is not found
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok().body(resource);  // Return image as response
    }
}
