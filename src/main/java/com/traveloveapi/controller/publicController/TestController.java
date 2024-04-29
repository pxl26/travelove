package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.HealthCheckResponse;
import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.mq.RabbitMQConfig;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.aws.s3.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private Queue queue;
    final private RabbitTemplate rabbitTemplate;


    @Tag(name = "Load testing")
    @GetMapping("/empty-get")
    public HealthCheckResponse emptyGet() {
        return new HealthCheckResponse("UP");
    }

    @Tag(name = "Load testing")
    @PostMapping("/empty-post")
    public String emptyPost(@RequestBody ViewedTourDTO data) {
        return "Ok";
    }

    @PostMapping("/mq")
    public String post(@RequestParam String message) {
        rabbitTemplate.convertAndSend("booking", message);
        return "Oke";
    }
}
