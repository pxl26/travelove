package com.traveloveapi.service.file;

import com.traveloveapi.utility.FileHandler;
import com.traveloveapi.utility.FileSupporter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {
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

    public String check(String path) {
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
