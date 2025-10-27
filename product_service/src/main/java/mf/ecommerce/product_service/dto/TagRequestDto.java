package mf.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mf.ecommerce.product_service.validators.CreateValidationGroup;

@Getter
@AllArgsConstructor
public class TagRequestDto {

    @NotBlank(message = "Name is required", groups = CreateValidationGroup.class)
    private String name;
    @Size(max = 255, message = "Description too long")
    @NotBlank(message = "Description is required", groups = CreateValidationGroup.class)
    private String description;
}
