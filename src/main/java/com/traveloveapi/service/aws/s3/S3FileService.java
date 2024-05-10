package com.traveloveapi.service.aws.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;
import com.traveloveapi.configuration.AWSConfig;
import com.traveloveapi.exception.SaveFileException;
import com.traveloveapi.utility.FileSupporter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;

@Service
public class S3FileService {
    private TransferManager transferManager;
    private AWSConfig config;
    private AmazonS3 client;

    @Value("${aws.s3.bucket_name}")
    private String bucket;
    
    S3FileService(AWSConfig config) {
        this.client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_1).withCredentials(config.getCredentialsProvider()).build();
        this.transferManager = TransferManagerBuilder.standard().withS3Client(client).build();
    }


//    public String uploadFile(File file, String path) {
//        String file_name = file.getName();
//        String extension = FileSupporter.getExtensionName(file_name);
//        String file_id = UUID.randomUUID().toString();
//        String key = path + file_id+'.'+extension;
//        fileUpload(bucket,key,file);
//        file.delete();
//        return file_id+'.'+extension;
//    }

//    private UploadResult fileUpload(String bucket_name, String key, File file)  {
//        try {
//            return transferManager.upload(bucket_name,key,file).waitForUploadResult();
//        }
//            catch (Exception ex) {
//                System.out.println(ex);
//            }
//        return null;
//    }


    public String uploadFile(MultipartFile file, String path, String file_name)  {
        String full_name = file_name + '.' + FileSupporter.getExtensionName(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println(file_name);
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(file.getSize());
            transferManager.upload(bucket, path + full_name,file.getInputStream(),  meta).waitForUploadResult();
            String extension = FileSupporter.getExtensionName(full_name);
            String[] video_extension = {"mp4","mov", "mkv"};
            for (String ex : video_extension)
                if (ex.equals(extension)) {
                    createThumbnail(file, full_name, file_name);
                    FileInputStream stream = new FileInputStream(file_name + '_' + "temp_thumb.png");
                    transferManager.upload(bucket, path + file_name + ".png", new File(file_name + '_' + "temp_thumb.png")).waitForUploadResult();
                    FileUtils.deleteDirectory(new File(path));
                }
        }
        catch (Exception ex) {
            System.out.println(ex);
            throw new SaveFileException();
        }
        return  (path + full_name).replace(" ", "%20");
    }


    public byte[] downloadFile(String path) {
        try {
            S3Object o  = client.getObject(bucket, path);
            S3ObjectInputStream objectInputStream = o.getObjectContent();
            return objectInputStream.readAllBytes();
        }
            catch (Exception ex) {
                System.out.println(ex);
                return null;
            }
    }

    private void createThumbnail(MultipartFile file, String full_name, String id) {
        try {
            OutputStream outStream = new FileOutputStream(new File("temp.mp4"));
            outStream.write(file.getInputStream().readAllBytes());
            outStream.close();
            FFmpegFrameGrabber g = new FFmpegFrameGrabber("temp.mp4");
            //FFmpegFrameGrabber g = new FFmpegFrameGrabber(new File("temp.mp4").getAbsoluteFile());
            g.setFormat(FileSupporter.getExtensionName(full_name));
            g.start();
//            ImageIO.write(g.grab().getBufferedImage(), "png", new File(id +'_'+ "temp_thumb.png"));
            File my_file = new File(id +'_'+ "temp_thumb.png");
            Java2DFrameConverter c = new Java2DFrameConverter();
            ImageIO.write(c.convert(g.grabImage()), "png", new File(id +'_'+ "temp_thumb.png"));
            g.stop();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
