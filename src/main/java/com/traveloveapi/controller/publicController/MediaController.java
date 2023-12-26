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
import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.service.file.FileService;
import com.traveloveapi.utility.FileHandler;
import com.traveloveapi.utility.FileSupporter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/public/media")
@RequiredArgsConstructor
public class MediaController {
    final private FileService fileService;
    @GetMapping
    @Operation(hidden = true)
    public ResponseEntity<byte[]> getFile(@RequestParam String file_name) {
        String extension = FileSupporter.getExtensionName(file_name);
        String contentType = FileSupporter.getContentTypeByExtension(extension);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type",contentType);
        return ResponseEntity.status(200).headers(headers).body(fileService.loadPublicFile(file_name));
    }

    @PostMapping
    @Operation(hidden = true)
    public SimpleResponse upFile(@RequestParam MultipartFile file) {
        String file_url = "/public/media?file_name=" + fileService.savePublicImage(file);
        return new SimpleResponse(file_url, 200);
    }
    @PostMapping("/s3")
    public String postDataToS3(@RequestParam MultipartFile file) throws IOException {
        AWSCredentials credential = new BasicAWSCredentials("AKIA3AG4GPMUVZLUKBNS","CvFEWS6GXqdaRkAEuYYTaOpCIifkMTGAbxnP6ha4");
        AmazonS3 s3Client = new AmazonS3Client(credential);
        s3Client.putObject(new PutObjectRequest("travelove-data","public/origin_name"+file.getOriginalFilename(), convertMultiPartToFile(file)));
        return "";
    }
    @GetMapping("/s3")
    private File convertMultiPartToFile(MultipartFile file) throws IOException, IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
