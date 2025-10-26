package mf.ecommerce.inventory_service.mapper;

import mf.ecommerce.inventory_service.dto.AddressRequestDto;
import mf.ecommerce.inventory_service.dto.AddressResponseDto;
import mf.ecommerce.inventory_service.model.Address;

public class AddressMapper {

    public static Address toAddress(AddressRequestDto dto) {
        return Address.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .reference(dto.getReference())
                .district(dto.getDistrict())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .zipCode(dto.getZipCode())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }

    public static AddressResponseDto toDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .reference(address.getReference())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }

    public static Address update(Address address, AddressRequestDto dto) {
        if (dto.getStreet() != null) {address.setStreet(dto.getStreet());}
        if (dto.getNumber() != null) {address.setNumber(dto.getNumber());}
        if (dto.getComplement() != null) {address.setComplement(dto.getComplement());}
        if (dto.getReference() != null) {address.setReference(dto.getReference());}
        if (dto.getDistrict() != null) {address.setDistrict(dto.getDistrict());}
        if (dto.getCity() != null) {address.setCity(dto.getCity());}
        if (dto.getState() != null) {address.setState(dto.getState());}
        if (dto.getCountry() != null) {address.setCountry(dto.getCountry());}
        if (dto.getZipCode() != null) {address.setZipCode(dto.getZipCode());}
        if (dto.getLatitude() != null) {address.setLatitude(dto.getLatitude());}
        if (dto.getLongitude() != null) {address.setLongitude(dto.getLongitude());}
        return address;
    }
}
