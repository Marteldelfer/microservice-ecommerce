package mf.ecommerce.product_service.service;

import lombok.AllArgsConstructor;
import mf.ecommerce.product_service.dto.ImageSrcRequestDto;
import mf.ecommerce.product_service.dto.ImageSrcResponseDto;
import mf.ecommerce.product_service.exception.FileSizeExcededLimitException;
import mf.ecommerce.product_service.exception.ImageSrcNotFoundException;
import mf.ecommerce.product_service.exception.InvalidFileFormatException;
import mf.ecommerce.product_service.mapper.ImageSrcMapper;
import mf.ecommerce.product_service.model.ImageSrc;
import mf.ecommerce.product_service.model.Product;
import mf.ecommerce.product_service.repository.ImageSrcRepository;
import mf.ecommerce.product_service.storage.ImageStorageService;
import mf.ecommerce.product_service.storage.ImageUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageSrcService {

    private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/jpg", "image/png", "image/webp");

    private ImageSrcRepository imageSrcRepository;
    private ImageStorageService storageService;

    public ImageSrcResponseDto getImageSrc(UUID id) {
        return ImageSrcMapper.toDto(imageSrcRepository.findById(id).orElseThrow(
                () -> new ImageSrcNotFoundException("Image src with id " + id + " not found")
        ));
    }

    public ImageSrcResponseDto getImageSrcBySlug(String slug) {
        return ImageSrcMapper.toDto(imageSrcRepository.findBySlug(slug).orElseThrow(
                () -> new ImageSrcNotFoundException("Image src with slug " + slug + " not found")
        ));
    }

    public ImageSrcResponseDto createImageSrc(ImageSrcRequestDto dto, Product product) {
        validateImage(dto.getImage());
        ImageUploadResponse uploadResponse = storageService.uploadImage(dto.getImage());
        ImageSrc imageSrc = ImageSrcMapper.toEntity(dto);

        imageSrc.setUrl(uploadResponse.getUrl());
        imageSrc.setImageKey(uploadResponse.getKey());
        imageSrc.setProduct(product);
        return ImageSrcMapper.toDto(imageSrcRepository.save(imageSrc));
    }

    public void deleteImageSrc(UUID id) {
        ImageSrc imageSrc = imageSrcRepository.findById(id).orElseThrow(
                () -> new ImageSrcNotFoundException("Image src with id " + id + " not found")
        );
        storageService.deleteImage(imageSrc.getUrl());
        imageSrcRepository.delete(imageSrc);
    }

    public ImageSrcResponseDto updateImageSrc(UUID id, ImageSrcRequestDto dto, Product product) {
        deleteImageSrc(id);
        return createImageSrc(dto, product);
    }

    private void validateImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new InvalidFileFormatException("Image file is empty");
        }
        long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5 MB
        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new FileSizeExcededLimitException("Image size exceeded limit of " + MAX_IMAGE_SIZE + " bytes");
        }
        if (!ALLOWED_TYPES.contains(image.getContentType())) {
            throw new InvalidFileFormatException("File should be jpeg, png or webp");
        }
    }
}
