package com.traveloveapi.controller.publicController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.file.FileService;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

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

    @GetMapping("/random-wall-paper")
    @Tag(name = "MEDIA")
    public ResponseEntity<byte[]> randomImage() {
        List<S3ObjectSummary> object_list =  s3FileService.getRandomWallPaper();
        Random random = new Random();
        int file_seq = random.nextInt( object_list.size()-1);
        S3ObjectSummary object = object_list.get(file_seq);
        String extension = FileSupporter.getExtensionName(object.getKey());
        String contentType = FileSupporter.getContentTypeByExtension(extension);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type",contentType);
        return ResponseEntity.status(200).headers(headers).body(s3FileService.downloadFile(object.getBucketName(), object.getKey()));
    }

    @PostMapping
    @Operation(hidden = true)
    public String uploadToS3(@RequestParam MultipartFile file) throws IOException, InterruptedException {
        return s3FileService.savePublicFile(file);
    }

    @GetMapping
    @Tag(name = "MEDIA")
    public ResponseEntity<byte[]> getFileFromS3(@RequestParam String file_name) throws InterruptedException {
        String extension = FileSupporter.getExtensionName(file_name);
        String contentType = FileSupporter.getContentTypeByExtension(extension);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type",contentType);
        return ResponseEntity.status(200).headers(headers).body(s3FileService.downloadFile("travelove-data", file_name));
    }
}
