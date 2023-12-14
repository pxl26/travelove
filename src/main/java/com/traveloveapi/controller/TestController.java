package com.traveloveapi.controller;

import com.traveloveapi.service.BillService;
import com.traveloveapi.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class TestController {
    private final FileService fileService;
    private final BillService billService;
    @GetMapping("/assetlinks.json")
    public ResponseEntity<byte[]> getFile() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/json");
        return ResponseEntity.status(200).headers(headers).body(fileService.loadPublicFile("assetlinks.json"));
    }
}
