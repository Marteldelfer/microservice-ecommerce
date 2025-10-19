package mf.ecommerce.product_service.storage;

import mf.ecommerce.product_service.exception.ImageDeletionFailedException;
import mf.ecommerce.product_service.exception.ImageUploadFailedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageStorageService {

    /**
     * Uploads image to external cloud
     * @param file image file to be uploaded
     * @return url and key of hosted image
     * @throws ImageUploadFailedException when image upload to external API fails
     */
    ImageUploadResponse uploadImage(MultipartFile file) throws ImageUploadFailedException;


    /**
     * Deletes image from external cloud
     * @param key key of image to be deleted
     * @throws ImageDeletionFailedException when deletion in external API fails
     */
    void deleteImage(String key) throws ImageDeletionFailedException;

    /**
     * Upload images by batch
     * @param files list of image files to be uploaded
     * @return list of urls and keys of hosted images
     * @throws ImageUploadFailedException when image upload to external API fails
     */
    List<ImageUploadResponse> uploadImageBatch(List<MultipartFile> files) throws ImageUploadFailedException;

    /**
     * Deletes images by batch
     * @param keys list of keys of images to be deleted
     * @throws ImageDeletionFailedException when deletion in external API fails
     */
    void deleteImageBatch(List<String> keys) throws ImageDeletionFailedException;
}
