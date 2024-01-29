package com.traveloveapi.controller.publicController;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.local_file.FileService;
import com.traveloveapi.utility.FileHandler;
import com.traveloveapi.utility.FileSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/public/media")
@RequiredArgsConstructor
public class MediaController {
    final private FileService fileService;
    final private S3FileService s3FileService;
//    @GetMapping
//    @Operation(hidden = true)
//    public ResponseEntity<byte[]> getFile(@RequestParam String file_name) {
//        String extension = FileSupporter.getExtensionName(file_name);
//        String contentType = FileSupporter.getContentTypeByExtension(extension);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type",contentType);
//        return ResponseEntity.status(200).headers(headers).body(fileService.loadPublicFile(file_name));
//    }
//
//    @PostMapping
//    @Operation(hidden = true)
//    public SimpleResponse upFile(@RequestParam MultipartFile file) {
//        String file_url = "/public/media?file_name=" + fileService.savePublicImage(file);
//        return new SimpleResponse(file_url, 200);
//    }


    @GetMapping
    @Tag(name = "MEDIA")
    public ResponseEntity<byte[]> getFileFromS3(@RequestParam String path) throws InterruptedException {
        String extension = FileSupporter.getExtensionName(path);
        String contentType = FileSupporter.getContentTypeByExtension(extension);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type",contentType);
        return ResponseEntity.status(200).headers(headers).body(s3FileService.downloadFile(path));
    }

    @PostMapping("/multiple")
    public String test(@RequestParam MultipartFile[] files) {
        ArrayList<File> list = new ArrayList<>();
        for (int i=0;i<files.length;i++)
        {
            list.add(FileHandler.convertMultiPartToFile(files[i]));
            System.out.println(list.get(i).getName());
            System.out.println(list.get(i).length());
        }
        s3FileService.multipleFileUpload("public/test", list);
        return "OK";
    }
}
