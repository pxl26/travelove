package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.aws.s3.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private S3FileService service;

    @Tag(name = "Load testing")
    @GetMapping("/empty-get")
    public String emptyGet() {
        return "Ok";
    }

    @Tag(name = "Load testing")
    @PostMapping("/empty-post")
    public String emptyPost(@RequestBody ViewedTourDTO data) {
        return "Ok";
    }

    @Operation(hidden = true)
    @GetMapping("/thumb")
    public void getThumb(MultipartFile file) throws FrameGrabber.Exception, IOException {
        service.uploadFile(file, "test/", UUID.randomUUID().toString());
    }
}
