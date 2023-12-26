package com.traveloveapi.service.aws.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.traveloveapi.configuration.AWSConfig;
import com.traveloveapi.utility.FileHandler;
import com.traveloveapi.utility.FileSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3FileService {
    private TransferManager transferManager;
    private AWSConfig config;
    private AmazonS3 client;
    S3FileService(AWSConfig config) {
        this.client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_1).withCredentials(config.getCredentialsProvider()).build();
        this.transferManager = TransferManagerBuilder.standard().withS3Client(client).build();
    }


    public String savePublicFile(MultipartFile file) throws IOException {
        String file_name = file.getOriginalFilename();
        String extension = FileSupporter.getExtensionName(file_name);
        String file_id = UUID.randomUUID().toString();
        String key = "/public/"+file_id+'.'+extension;
        fileUpload("travelove-data",key,FileHandler.convertMultiPartToFile(file));
        return "/public/media?file_name="+key;
    }

    public byte[] getPublicFile(String file_name) {
        return downloadFile("travelove-data", "/public/"+file_name);
    }
    private UploadResult fileUpload(String bucket_name, String key, File file)  {
        try {
            return transferManager.upload(bucket_name,key,file).waitForUploadResult();
        }
            catch (Exception ex) {
                System.out.println(ex);
                return null;
            }
    }

    public List<S3ObjectSummary> getRandomWallPaper() {
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName("travelove-data").withPrefix("wall-paper");
        ListObjectsV2Result rs = client.listObjectsV2(req);
        return rs.getObjectSummaries();
    }

    private void multipleFileUpload(String bucket_name, String key_prefix, ArrayList<File> file_list) throws InterruptedException {
        transferManager.uploadFileList(bucket_name, key_prefix, new File("."),file_list).waitForCompletion();
    }

    public byte[] downloadFile(String bucket, String key) {
        File file = new File("./temp_s3/"+key);
        try {
            transferManager.download(bucket, key,file).waitForCompletion();
            return FileHandler.loadFile("./temp_s3/"+key);
        }
            catch (Exception ex) {
                System.out.println(ex);
                return null;
            }
    }
}
