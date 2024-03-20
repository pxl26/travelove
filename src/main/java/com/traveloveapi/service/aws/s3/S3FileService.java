package com.traveloveapi.service.aws.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.traveloveapi.configuration.AWSConfig;
import com.traveloveapi.exception.SaveFileException;
import com.traveloveapi.utility.FileHandler;
import com.traveloveapi.utility.FileSupporter;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        try {
            transferManager.upload(bucket, path + full_name,file.getInputStream(),  null).waitForUploadResult();
            String extension = FileSupporter.getExtensionName(full_name);
            String[] video_extension = {"mp4","mov", "mkv"};
            for (String ex : video_extension)
                if (ex.equals(extension)) {
                    createThumbnail(file, full_name, file_name, path);
                    transferManager.upload(bucket, path + file_name + ".png", new FileInputStream(file_name + '_' + "temp_thumb.png"), null).waitForUploadResult();
                    FileUtils.deleteDirectory(new File(path));
                }
        }
        catch (Exception ex) {
            throw new SaveFileException();
        }
        return  (path + full_name).replace(" ", "%20");
    }

    public List<S3ObjectSummary> getRandomWallPaper() {
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket).withPrefix("wall-paper");
        ListObjectsV2Result rs = client.listObjectsV2(req);
        return rs.getObjectSummaries();
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

    private void createThumbnail(MultipartFile file, String full_name, String id,String path) {
        try {
            OutputStream outStream = new FileOutputStream(new File("temp.mp4"));
            outStream.write(file.getInputStream().readAllBytes());
            FFmpegFrameGrabber g = new FFmpegFrameGrabber(new File("temp.mp4"));
            g.setFormat(FileSupporter.getExtensionName(full_name));
            g.start();
            ImageIO.write(g.grab().getBufferedImage(), "png", new File(id +'_'+ "temp_thumb.png"));
            g.stop();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
