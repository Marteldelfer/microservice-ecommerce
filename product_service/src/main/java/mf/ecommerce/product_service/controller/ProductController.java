package mf.ecommerce.product_service.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mf.ecommerce.product_service.dto.*;
import mf.ecommerce.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto response =  productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @Valid @RequestBody ProductRequestDto requestDto,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/link/tag")
    public ResponseEntity<ProductResponseDto> linkTag(@Valid @RequestBody LinkTagRequestDto requestDto) {
        return ResponseEntity.ok(productService.linkTag(requestDto));
    }

    @PutMapping("/link/category")
    public ResponseEntity<ProductResponseDto> linkCategory(@Valid @RequestBody LinkCategoryRequestDto requestDto) {
        return ResponseEntity.ok(productService.linkCategory(requestDto));
    }

    @PutMapping("/link/image/{productId}")
    public ResponseEntity<ProductResponseDto> linkImage(
            @Valid @ModelAttribute ImageSrcRequestDto imageSrcRequestDto,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(productService.linkImageSrc(productId, imageSrcRequestDto));
    }

    @PutMapping("/unlink/tag")
    public ResponseEntity<ProductResponseDto> unlinkTag(@Valid @RequestBody LinkTagRequestDto requestDto) {
        return ResponseEntity.ok(productService.unlinkTag(requestDto));
    }

    @PutMapping("/unlink/category")
    public ResponseEntity<ProductResponseDto> unlinkCategory(@Valid @RequestBody LinkCategoryRequestDto requestDto) {
        return ResponseEntity.ok(productService.unlinkCategory(requestDto));
    }

    @PutMapping("/unlink/image")
    public ResponseEntity<ProductResponseDto> unlinkImage(
            @Valid @RequestBody LinkImageSrcRequestDto requestDto
    ) {
        return ResponseEntity.ok(productService.unlinkImageSrc(requestDto));
    }

    @PutMapping("/link/main-image")
    public ResponseEntity<ProductResponseDto> updateImage(
            @Valid @RequestBody LinkImageSrcRequestDto requestDto
    ) {
        return ResponseEntity.ok(productService.updateMainImage(requestDto));
    }
}
