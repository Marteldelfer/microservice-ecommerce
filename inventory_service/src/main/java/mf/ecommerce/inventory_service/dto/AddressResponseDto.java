package mf.ecommerce.inventory_service.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AddressResponseDto {

    private UUID id;

    private String street;
    private String number;
    private String complement;
    private String reference;
    private String district;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    private Double latitude;
    private Double longitude;
}
