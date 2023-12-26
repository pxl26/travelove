package com.traveloveapi.service.file;

import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.utility.FileHandler;
import com.traveloveapi.utility.FileSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    final private MediaRepository mediaRepository;
    final private S3FileService s3FileService;
    public String  savePublicImage(MultipartFile file) {
        String file_name = file.getOriginalFilename();
        String extension = FileSupporter.getExtensionName(file_name);
        String file_id = UUID.randomUUID().toString();
        String path = "./data/public";
        FileHandler.saveFile(path, file_id + "." + extension, file);
        // Return url for access this file
        return "/public/media?file_name=" + file_id + "." + extension;
    }
    public byte[] loadPublicFile(String file_name) {
        return FileHandler.loadFile("./data/public/"+file_name);
    }

    public MediaEntity saveMedia(MultipartFile file, String description, String ref_id, String type) throws IOException {
        MediaEntity entity = new MediaEntity();
        entity.setType(type);
        entity.setId(UUID.randomUUID().toString());
        entity.setRef_id(ref_id);
        entity.setDescription(description);
        entity.setPath(s3FileService.savePublicFile(file));
        mediaRepository.save(entity);
        return entity;
    }
    private String check(String path) {
        String rs = "";
        File file = new File(path);
        if (file.isDirectory()) {
            rs += "Parent: " + file.getParent() + "\n";
            rs +="Absolute: " + file.getAbsolutePath();
        }
        else
            rs = "File!!!";
        return rs;
    }

}
