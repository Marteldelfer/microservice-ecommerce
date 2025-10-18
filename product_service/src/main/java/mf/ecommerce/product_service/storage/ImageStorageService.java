package mf.ecommerce.product_service.storage;

import mf.ecommerce.product_service.exception.ImageDeletionFailedException;
import mf.ecommerce.product_service.exception.ImageUploadFailedException;
import mf.ecommerce.product_service.exception.InvalidFileFormatException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    /**
     * @param file image file to be uploaded
     * @return url where image is hosted
     * @throws ImageUploadFailedException when image upload to external API fails
     * @throws InvalidFileFormatException when file is not image (jpeg or png) or is outside size limits
     */
    String uploadImage(MultipartFile file) throws ImageUploadFailedException, InvalidFileFormatException;


    /**
     * @param key key of image to be deleted
     * @throws ImageDeletionFailedException when deletion in external API fails
     */
    void deleteImage(String key) throws ImageDeletionFailedException;
}
