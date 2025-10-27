package mf.ecommerce.inventory_service.mapper;

import mf.ecommerce.inventory_service.dto.ProductProviderRequestDto;
import mf.ecommerce.inventory_service.dto.ProductProviderResponseDto;
import mf.ecommerce.inventory_service.model.ProductProvider;

public class ProductProviderMapper {

    public static ProductProviderResponseDto toDto(ProductProvider productProvider) {
        return ProductProviderResponseDto.builder()
                .id(productProvider.getId())
                .name(productProvider.getName())
                .description(productProvider.getDescription())
                .email(productProvider.getEmail())
                .phone(productProvider.getPhone())
                .address(AddressMapper.toDto(productProvider.getAddress()))
                .build();
    }

    public static ProductProvider toEntity(ProductProviderRequestDto dto) {
        return ProductProvider.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(AddressMapper.toAddress(dto.getAddress()))
                .build();
    }

    public static ProductProvider update(ProductProvider provider, ProductProviderRequestDto dto) {
        if (dto.getName() != null) {provider.setName(dto.getName());}
        if (dto.getDescription() != null) {provider.setDescription(dto.getDescription());}
        if (dto.getEmail() != null) {provider.setEmail(dto.getEmail());}
        if (dto.getPhone() != null) {provider.setPhone(dto.getPhone());}
        return provider;
    }
}
