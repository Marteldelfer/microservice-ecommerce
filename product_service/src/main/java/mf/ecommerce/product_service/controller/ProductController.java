package mf.ecommerce.product_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import mf.ecommerce.product_service.dto.*;
import mf.ecommerce.product_service.service.ProductService;
import mf.ecommerce.product_service.validators.CreateValidationGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "API for managing products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get all products by name")
    public ResponseEntity<List<ProductResponseDto>> getProductsByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }

    @PostMapping
    @Operation(summary = "Create new product")
    public ResponseEntity<ProductResponseDto> createProduct(
            @Validated({CreateValidationGroup.class, Default.class}) @RequestBody ProductRequestDto requestDto
    ) {
        ProductResponseDto response =  productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @Validated({Default.class}) @RequestBody ProductRequestDto requestDto,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/link/tag")
    @Operation(summary = "Link tag to product")
    public ResponseEntity<ProductResponseDto> linkTag(@Valid @RequestBody LinkTagRequestDto requestDto) {
        return ResponseEntity.ok(productService.linkTag(requestDto));
    }

    @PutMapping("/link/category")
    @Operation(summary = "Link category to product")
    public ResponseEntity<ProductResponseDto> linkCategory(@Valid @RequestBody LinkCategoryRequestDto requestDto) {
        return ResponseEntity.ok(productService.linkCategory(requestDto));
    }

    @PutMapping("/link/image/{productId}")
    @Operation(summary = "Create and link image to product")
    public ResponseEntity<ProductResponseDto> linkImage(
            @Valid @ModelAttribute ImageSrcRequestDto imageSrcRequestDto,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(productService.linkImageSrc(productId, imageSrcRequestDto));
    }

    @PutMapping("/unlink/tag")
    @Operation(summary = "Unlink tag to product")
    public ResponseEntity<ProductResponseDto> unlinkTag(@Valid @RequestBody LinkTagRequestDto requestDto) {
        return ResponseEntity.ok(productService.unlinkTag(requestDto));
    }

    @PutMapping("/unlink/category")
    @Operation(summary = "Unlink category to product")
    public ResponseEntity<ProductResponseDto> unlinkCategory(@Valid @RequestBody LinkCategoryRequestDto requestDto) {
        return ResponseEntity.ok(productService.unlinkCategory(requestDto));
    }

    @PutMapping("/unlink/image")
    @Operation(summary = "Unlink image to product")
    public ResponseEntity<ProductResponseDto> unlinkImage(
            @Valid @RequestBody LinkImageSrcRequestDto requestDto
    ) {
        return ResponseEntity.ok(productService.unlinkImageSrc(requestDto));
    }

    @PutMapping("/link/main-image")
    @Operation(summary = "Link main thumbnail to product")
    public ResponseEntity<ProductResponseDto> updateImage(
            @Valid @RequestBody LinkImageSrcRequestDto requestDto
    ) {
        return ResponseEntity.ok(productService.updateMainImage(requestDto));
    }
}
