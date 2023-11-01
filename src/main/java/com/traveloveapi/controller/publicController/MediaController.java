package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.service.file.FileService;
import com.traveloveapi.utility.FileSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/public/media")
@RequiredArgsConstructor
public class MediaController {
    final private FileService fileService;
    @GetMapping
    public ResponseEntity<byte[]> getFile(@RequestParam String file_name) {
        String extension = FileSupporter.getExtensionName(file_name);
        String contentType = FileSupporter.getContentTypeByExtension(extension);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type",contentType);
        return ResponseEntity.status(200).headers(headers).body(fileService.loadPublicFile(file_name));
    }

    @PostMapping
    public SimpleResponse upFile(@RequestParam MultipartFile file) {
        String file_url = "/public/media?file_name=" + fileService.savePublicImage(file);
        return new SimpleResponse(file_url, 200);
    }
}
