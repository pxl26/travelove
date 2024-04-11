package com.traveloveapi.controller.report;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.constrain.Platform;
import com.traveloveapi.constrain.ReportStatus;
import com.traveloveapi.constrain.ReportType;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.report.ReportEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.report.ReportRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {
    final private S3FileService s3FileService;
    final private MediaRepository mediaRepository;
    final private ReportRepository reportRepository;
    final private UserService userService;

    @Tag(name = "SPRINT 10")
    @PostMapping
    public ReportEntity createReport(@RequestParam String content, @RequestParam(required = false) String tour_id, @RequestParam MultipartFile[] attachment_list, @RequestParam Platform platform, @RequestParam ReportType type) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(UUID.randomUUID().toString());
        reportEntity.setReporter_id(SecurityContext.getUserID());
        reportEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        reportEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        reportEntity.setStatus(ReportStatus.PENDING);
        reportEntity.setContent(content);
        reportEntity.setPlatform(platform);
        reportEntity.setType(type);
        if (tour_id != null)
            reportEntity.setTour_id(tour_id);
        int k=0;
        for (MultipartFile attachment : attachment_list) {
            MediaEntity media = new MediaEntity();
            media.setId(UUID.randomUUID().toString());
            media.setSeq(k++);
            media.setRef_id(reportEntity.getId());
            media.setType("REPORT-ATTACHMENT");
            media.setPath(s3FileService.uploadFile(attachment, "public/report/"+reportEntity.getId()+'/',media.getId()));
            mediaRepository.save(media);
        }
        reportRepository.save(reportEntity);
        return reportEntity;
    }

    @Tag(name = "SPRINT 10 - MANAGE")
    @GetMapping
    public List<ReportEntity> getReport(@RequestParam(required = false) ReportStatus status, @RequestParam int page) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        if (status != null)
            return reportRepository.findAll(page, status);
        else return reportRepository.findAll(page);
    }

    @Tag(name = "SPRINT 10 - MANAGE")
    @GetMapping("/attachment")
    public List<MediaEntity> getReportAttachment(@RequestParam String report_id) {
        return mediaRepository.find(report_id, "REPORT-ATTACHMENT");
    }

    @Tag(name = "SPRINT 10 - MANAGE")
    @PutMapping
    public SimpleResponse updateReport(@RequestParam ReportStatus status, @RequestParam String report_id) {
        reportRepository.update(status, report_id);
        return new SimpleResponse("Report updated");
    }
}
