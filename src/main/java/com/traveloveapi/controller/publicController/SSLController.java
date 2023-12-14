package com.traveloveapi.controller.publicController;

import com.traveloveapi.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SSLController {
    final private FileService fileService;
    @GetMapping("/.well-known/pki-validation/B693896016E01B5DDD76680770EA7FBB.txt")
    public byte[] requireForSSL() {
        return fileService.loadPublicFile("ssl.txt");
    }
}
