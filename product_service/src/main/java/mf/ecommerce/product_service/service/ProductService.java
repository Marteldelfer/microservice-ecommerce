package mf.ecommerce.product_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.dto.ProductRequestDto;
import mf.ecommerce.product_service.dto.ProductResponseDto;
import mf.ecommerce.product_service.exception.ProductNotFoundException;
import mf.ecommerce.product_service.mapper.ProductMapper;
import mf.ecommerce.product_service.model.Product;
import mf.ecommerce.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

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
        return ProductMapper.toDto(productRepository.save(ProductMapper.toProduct(dto)));
    }

    public ProductResponseDto updateProduct(UUID id, ProductRequestDto dto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + id + " not found")
        );
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        return ProductMapper.toDto(productRepository.save(product));
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
