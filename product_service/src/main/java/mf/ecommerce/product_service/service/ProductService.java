package mf.ecommerce.product_service.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.dto.*;
import mf.ecommerce.product_service.exception.ImageSrcDoesNotBelongToProductException;
import mf.ecommerce.product_service.exception.ProductNotFoundException;
import mf.ecommerce.product_service.kafka.ProductEventProducer;
import mf.ecommerce.product_service.mapper.ProductMapper;
import mf.ecommerce.product_service.model.Category;
import mf.ecommerce.product_service.model.ImageSrc;
import mf.ecommerce.product_service.model.Product;
import mf.ecommerce.product_service.model.Tag;
import mf.ecommerce.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final TagService tagService;
    private final CategoryService categoryService;
    private final ImageSrcService imageSrcService;
    private final ProductEventProducer eventProducer;

    public ProductResponseDto getProductById(UUID id) {
        return ProductMapper.toDto(productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + id + " not found")
        ));
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toDto).toList();
    }

    public List<ProductResponseDto> getProductsByName(String name) {
        return productRepository.findAllByName(name).stream().map(ProductMapper::toDto).toList();
    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        log.info("Creating a new product with name {}", dto.getName());
        Product saved = productRepository.save(ProductMapper.toProduct(dto));
        eventProducer.sendProductCreatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    public ProductResponseDto updateProduct(UUID id, ProductRequestDto dto) {
        log.info("Updating a new product with id {}", id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + id + " not found")
        );
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    public void deleteProduct(UUID id) {
        log.info("Deleting product with id {}", id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + id + " not found")
        );
        eventProducer.sendProductDeletedEvent(product);
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductResponseDto linkTag(LinkTagRequestDto dto) {
        log.info("Link tag with id {} to product with id {}", dto.getTagId(), dto.getProductId());
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + dto.getProductId() + " not found")
        );
        Tag tag = tagService.linkProduct(dto.getTagId(), product);
        product.getTags().add(tag);
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    @Transactional
    public ProductResponseDto linkCategory(LinkCategoryRequestDto dto) {
        log.info("Link category with id {} to product with id {}", dto.getCategoryId(), dto.getProductId());
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + dto.getProductId() + " not found")
        );
        Category category = categoryService.linkCategory(dto.getCategoryId(), product);
        product.getCategories().add(category);
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    @Transactional
    public ProductResponseDto linkImageSrc(UUID id, ImageSrcRequestDto dto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + id + " not found")
        );
        ImageSrc imageSrc = imageSrcService.createImageSrc(dto, product);
        log.info("Link image src with id {} to product with id {}", imageSrc.getId(), id);
        if (product.getMainImage() == null) {
            log.info("Setting product main image to {}", imageSrc.getUrl());
            product.setMainImage(imageSrc.getUrl());
        }
        product.getImages().add(imageSrc);
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    @Transactional
    public ProductResponseDto unlinkTag(LinkTagRequestDto dto) {
        log.info("Unlinking tag with id {} to product with id {}", dto.getTagId(), dto.getProductId());
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + dto.getProductId() + " not found")
        );
        Tag tag = tagService.unlinkProduct(dto.getTagId(), product);
        product.getTags().remove(tag);
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    @Transactional
    public ProductResponseDto unlinkCategory(LinkCategoryRequestDto dto) {
        log.info("Unlinking category with id {} to product with id {}", dto.getCategoryId(), dto.getProductId());
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + dto.getProductId() + " not found")
        );
        Category category = categoryService.unlinkProduct(dto.getCategoryId(), product);
        product.getCategories().remove(category);
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    @Transactional
    public ProductResponseDto unlinkImageSrc(LinkImageSrcRequestDto dto) {
        log.info("Unlinking imageSrc with id {} to product with id {}", dto.getImageSrcId(), dto.getProductId());
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + dto.getProductId() + " not found")
        );
        if (product.getImages().stream().noneMatch(imageSrc -> imageSrc.getId().equals(dto.getImageSrcId()))) {
            throw new ImageSrcDoesNotBelongToProductException(
                    "Image with id " + dto.getImageSrcId() + " doesn't belong to product with id " + product.getId()
            );
        }
        ImageSrc imageSrc = imageSrcService.unlinkImageSrc(dto.getImageSrcId());
        product.getImages().remove(imageSrc);

        if (product.getMainImage().equals(imageSrc.getUrl())) {
            log.info("Product main image with url {} was deleted",  imageSrc.getUrl());
            if (!product.getImages().isEmpty()) {
                product.setMainImage(product.getImages().getFirst().getUrl());
            } else {
                product.setMainImage(null);
            }
        }
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }

    public ProductResponseDto updateMainImage(LinkImageSrcRequestDto dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + dto.getProductId() + " not found")
        );
        String url = imageSrcService.getImageSrcUrl(dto.getImageSrcId());
        if (product.getImages().stream().noneMatch(imageSrc -> imageSrc.getUrl().equals(url))) {
            throw new ImageSrcDoesNotBelongToProductException(
                    "Image with url " + url + " doesn't belong to product with id " + product.getId()
            );
        }
        log.info("Updating product with id {} main image to {}", product.getId(), url);
        product.setMainImage(url);
        Product saved = productRepository.save(product);
        eventProducer.sendProductUpdatedEvent(saved);
        return ProductMapper.toDto(saved);
    }
}
