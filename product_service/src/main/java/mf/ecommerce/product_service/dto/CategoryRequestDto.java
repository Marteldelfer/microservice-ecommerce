package mf.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "Name is required")
    private final String name;
    @Size(max = 255, message = "Description too long")
    private final String description;
}
