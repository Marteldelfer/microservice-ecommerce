package mf.ecommerce.product_service.storage;

import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.exception.ImageDeletionFailedException;
import mf.ecommerce.product_service.exception.ImageUploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AwsImageStorageService implements ImageStorageService {

    private final S3Client s3Client;
    private final String region;
    private final String bucket;

    public AwsImageStorageService(
            @Value("${aws.s3.region}") String region,
            @Value("${aws.s3.bucket-name}") String bucket) {
        this.region = region;
        this.bucket = bucket;
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file) throws ImageUploadFailedException {
        try {
            String key = UUID.randomUUID().toString();
            log.info("Uploading image to S3: {}", key);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
            return new ImageUploadResponse(key, url);
        } catch (S3Exception e) {
            throw new ImageUploadFailedException("Failed to upload image to S3: " + e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            throw new ImageUploadFailedException("Failed to read file bytes: " + e.getMessage());
        }
    }

    @Override
    public void deleteImage(String key) throws ImageDeletionFailedException {
        try {
            log.info("Deleting image from S3 with key: {}", key);
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new ImageUploadFailedException("Failed to delete image from S3: " + e.awsErrorDetails().errorMessage());
        }
    }

    @Override
    public List<ImageUploadResponse> uploadImageBatch(List<MultipartFile> files) throws ImageUploadFailedException {
        List<ImageUploadResponse> responses = new ArrayList<>(); // TODO implement proper batch upload
        for (MultipartFile file : files) {
            responses.add(uploadImage(file));
        }
        return responses;
    }

    @Override
    public void deleteImageBatch(List<String> keys) throws ImageDeletionFailedException {
        try {
            log.info("Deleting images from S3 with keys: {}", keys);
            List<ObjectIdentifier> objects = keys.stream()
                    .map(key -> ObjectIdentifier.builder().key(key).build())
                    .toList();
            Delete delete = Delete.builder().objects(objects).build();
            DeleteObjectsRequest deleteObjectRequest = DeleteObjectsRequest.builder()
                    .bucket(bucket)
                    .delete(delete)
                    .build();
            s3Client.deleteObjects(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new ImageDeletionFailedException("Failed to delete images from S3: " + e.awsErrorDetails().errorMessage());
        }
    }
}