package com.traveloveapi.service.file;

import com.traveloveapi.utility.FileHandler;
import com.traveloveapi.utility.FileSupporter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileService {
    public String  savePublicImage(MultipartFile file) {
        String file_name = file.getOriginalFilename();
        String extension = FileSupporter.getExtensionName(file_name);
        String file_id = UUID.randomUUID().toString();
        String path = "src/main/resources/public";
        FileHandler.saveFile(path, file_id + "." + extension, file);
        return file_id + "." + extension;
    }
    public byte[] loadPublicFile(String file_name) {
        return FileHandler.loadFile("src/main/resources/public/"+file_name);
    }
}
