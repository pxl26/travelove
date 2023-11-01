package com.traveloveapi.utility;

import com.traveloveapi.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class FileHandler {
    static public void saveFile(String path, String file_name, MultipartFile file) {
        try {
            File parentFolder = new File(path);
            boolean isExist = parentFolder.mkdirs();
            File myFile = new File(path + '/' + file_name);
            FileOutputStream out = new FileOutputStream(myFile);
            out.write(file.getBytes());
        } catch (Exception ex) {
            throw new FileException();
        }
    }
}
