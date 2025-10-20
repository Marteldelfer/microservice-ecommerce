package mf.ecommerce.product_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.exception.ProductNotFoundException;
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

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + id + " not found")
        );
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(
                () -> new ProductNotFoundException("Product with name: " + name + " not found")
        );
    }
}
