package com.traveloveapi.service.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.*;
import com.traveloveapi.configuration.AWSConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class S3FileService {
    private AmazonS3 client;
    private TransferManager transferManager;
    public S3FileService(AWSConfig config) {
        this.client = AmazonS3ClientBuilder.standard().withRegion(config.getRegion()).withCredentials(config.getCredentialsProvider()).build();
        this.transferManager = TransferManagerBuilder.standard().withS3Client(client).build();
    }
    public Upload fileUpload(String bucket_name, String key, File file) {
        return transferManager.upload(bucket_name,key,file);
    }

    public MultipleFileUpload multipleFileUpload(String bucket_name, String key_prefix, ArrayList<File> file_list) {
        return transferManager.uploadFileList(bucket_name, key_prefix, new File("."),file_list);
    }

    public File downloadFile(String bucket, String key) {
        return null;
    }

}
