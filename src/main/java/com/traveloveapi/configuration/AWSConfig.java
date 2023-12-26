package com.traveloveapi.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AWSConfig {
    @Value("${aws.s3.access_key}")
    private String access_key;
    @Value("${aws.s3.secret_key}")
    private String secret_key;
    @Value("${aws.s3.bucket_name}")
    private String bucket_name;
    @Value("${aws.region}")
    private String region;

    private AWSCredentialsProvider credentialsProvider;

    @PostConstruct
    public void setupCredential() {
        System.out.println("ACCESS_KEY: "+ access_key);
        credentialsProvider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return new BasicAWSCredentials(access_key,secret_key);
            }

            @Override
            public void refresh() {
            }
        };
    }
}
