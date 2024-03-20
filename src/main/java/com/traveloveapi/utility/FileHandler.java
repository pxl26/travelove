package com.traveloveapi.utility;

import com.traveloveapi.exception.LoadFileException;
import com.traveloveapi.exception.SaveFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class FileHandler {
    static public void saveFile(String path, String file_name, MultipartFile file) {
        try {
            File parentFolder = new File(path);
            boolean isExist = parentFolder.mkdirs();
            File myFile = new File(path + '/' + file_name);
            FileOutputStream out = new FileOutputStream(myFile);
            out.write(file.getBytes());
        } catch (Exception ex) {
            throw new SaveFileException();
        }
    }

    static public byte[] loadFile(String path) {
        try {
            FileInputStream in = new FileInputStream(path);
            return in.readAllBytes();
        } catch (Exception ex)  {throw new LoadFileException(); }
    }

    static public File convertMultiPartToFile(MultipartFile file)  {
        try {
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        }
        catch (Exception ex) { throw new SaveFileException(); }
    }

    static public File convertMultiPartToFile(MultipartFile file, String filename)  {
        try {
            File convFile = new File(filename);
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        }
        catch (Exception ex) { throw new SaveFileException(); }
    }
    static public ArrayList<File> convertMultipartToFile(MultipartFile[] list) {
        try {
            ArrayList<File> rs = new ArrayList<>();
            for (MultipartFile file : list)
                rs.add(FileHandler.convertMultiPartToFile(file));
            return rs;
        }
        catch (Exception ex) {
            throw new SaveFileException();
        }
    }
}

