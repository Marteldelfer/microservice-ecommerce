package mf.ecommerce.product_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.dto.CategoryRequestDto;
import mf.ecommerce.product_service.dto.CategoryResponseDto;
import mf.ecommerce.product_service.service.CategoryService;
import mf.ecommerce.product_service.validators.CreateValidationGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "API for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable UUID id) {
        CategoryResponseDto responseDto = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get category by name")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @PostMapping
    @Operation(summary = "Create new category")
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Validated({CreateValidationGroup.class, Default.class}) @RequestBody CategoryRequestDto requestDto
    ) {
        log.info("Creating new category {}, {}", requestDto.getName(), requestDto.getDescription());
        return ResponseEntity.ok(categoryService.createCategory(requestDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @Validated({Default.class}) @RequestBody CategoryRequestDto requestDto,
            @PathVariable UUID id
    ) {
        log.info("Updating category with id {}", id);
        return ResponseEntity.ok(categoryService.updateCategory(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        log.info("Deleting category with id {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
