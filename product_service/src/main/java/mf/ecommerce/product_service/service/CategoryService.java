package mf.ecommerce.product_service.service;

import lombok.AllArgsConstructor;
import mf.ecommerce.product_service.dto.CategoryRequestDto;
import mf.ecommerce.product_service.dto.CategoryResponseDto;
import mf.ecommerce.product_service.exception.CategoryNotFoundException;
import mf.ecommerce.product_service.mapper.CategoryMapper;
import mf.ecommerce.product_service.model.Category;
import mf.ecommerce.product_service.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDto getCategoryById(UUID id) {
        return CategoryMapper.toDto(categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id: " + id)
        ));
    }

    public CategoryResponseDto getCategoryByName(String name) {
        return CategoryMapper.toDto(categoryRepository.findByName(name).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with name: " + name)
        ));
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper::toDto).toList();
    }

    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.toCategory(dto)));
    }

    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id: " + id)
        );
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
