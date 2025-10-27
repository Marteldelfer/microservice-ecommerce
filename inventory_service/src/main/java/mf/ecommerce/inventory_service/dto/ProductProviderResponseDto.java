package mf.ecommerce.inventory_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductProviderResponseDto {

    private UUID id;
    private String name;
    private String description;
    private String email;
    private String phone;

    private AddressResponseDto address;

}
