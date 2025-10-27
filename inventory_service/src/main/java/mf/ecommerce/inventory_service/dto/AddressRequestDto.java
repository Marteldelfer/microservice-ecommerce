package mf.ecommerce.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mf.ecommerce.inventory_service.validators.CreateValidationGroup;

@Getter
@AllArgsConstructor
public class AddressRequestDto {

    @NotBlank(message = "Street is required", groups = {CreateValidationGroup.class})
    private String street;

    @NotBlank(message = "Number is required", groups = {CreateValidationGroup.class})
    private String number;

    @Size(max = 255, message = "Complement too long")
    private String complement;

    @Size(max = 255, message = "Reference too long")
    private String reference;

    @NotBlank(message = "District is required", groups = {CreateValidationGroup.class})
    private String district;

    @NotBlank(message = "City is required", groups = {CreateValidationGroup.class})
    private String city;

    @NotBlank(message = "State is required", groups = {CreateValidationGroup.class})
    private String state;

    @NotBlank(message = "Country is required", groups = {CreateValidationGroup.class})
    private String country;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Zip code must follow the pattern 00000-000", groups = {CreateValidationGroup.class})
    private String zipCode;

    private Double latitude;
    private Double longitude;
}
