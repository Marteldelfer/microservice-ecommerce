package mf.ecommerce.inventory_service.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.inventory_service.dto.AddressRequestDto;
import mf.ecommerce.inventory_service.dto.AddressResponseDto;
import mf.ecommerce.inventory_service.exception.AddressNotFoundException;
import mf.ecommerce.inventory_service.mapper.AddressMapper;
import mf.ecommerce.inventory_service.model.Address;
import mf.ecommerce.inventory_service.model.ProductProvider;
import mf.ecommerce.inventory_service.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressResponseDto getAddress(UUID id) {
        return AddressMapper.toDto(addressRepository.findById(id).orElseThrow(
                () -> new AddressNotFoundException("Address not found with id: " + id)
        ));
    }

    public List<AddressResponseDto> getAllAddresses() {
        return addressRepository.findAll().stream().map(AddressMapper::toDto).toList();
    }

    @Transactional
    public Address createAddress(AddressRequestDto dto, ProductProvider provider) {
        log.info("Creating address from request with zip code {}", dto.getZipCode());
        Address address = AddressMapper.toEntity(dto);
        address.setProvider(provider);
        return addressRepository.save(address);
    }

    @Transactional
    public AddressResponseDto updateAddress(UUID id, AddressRequestDto dto) {
        log.info("Updating address from request with id {}", id);
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new  AddressNotFoundException("Address not found with id: " + id)
        );
        return AddressMapper.toDto(addressRepository.save(AddressMapper.update(address, dto)));
    }
}
