package com.traveloveapi.controller;

import com.traveloveapi.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.well-known/assetlinks.json")
@RequiredArgsConstructor
public class TestController {
    private final FileService fileService;
    @GetMapping
    public ResponseEntity<byte[]> getFile() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/json");
        return ResponseEntity.status(200).headers(headers).body(fileService.loadPublicFile("assetlinks.json"));
    }
}
