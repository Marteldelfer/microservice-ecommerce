package mf.ecommerce.inventory_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mf.ecommerce.inventory_service.validator.CreateProductProviderValidationGroup;


@Getter
@AllArgsConstructor
public class ProductProviderRequestDto {

    @NotBlank(message = "Name is required", groups = CreateProductProviderValidationGroup.class)
    private String name;
    private String description;
    @NotBlank(message = "Email is required", groups = CreateProductProviderValidationGroup.class)
    @Email(message = "Should be valid email", groups = CreateProductProviderValidationGroup.class)
    private String email;

    @Pattern(
            regexp = "^\\d{8,15}$", message = "Should be valid phone number",
            groups = CreateProductProviderValidationGroup.class
    )
    private String phone;

    @NotNull(message = "Address is required", groups = CreateProductProviderValidationGroup.class)
    private AddressRequestDto address;

}
