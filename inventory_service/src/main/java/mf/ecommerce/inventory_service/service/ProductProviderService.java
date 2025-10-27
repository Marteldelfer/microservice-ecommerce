package mf.ecommerce.inventory_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.inventory_service.dto.ProductProviderRequestDto;
import mf.ecommerce.inventory_service.dto.ProductProviderResponseDto;
import mf.ecommerce.inventory_service.exception.ProductProviderNotFoundException;
import mf.ecommerce.inventory_service.mapper.ProductProviderMapper;
import mf.ecommerce.inventory_service.model.Address;
import mf.ecommerce.inventory_service.model.InventoryItem;
import mf.ecommerce.inventory_service.model.ProductProvider;
import mf.ecommerce.inventory_service.repository.ProductProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductProviderService {

    private final ProductProviderRepository providerRepository;
    private final AddressService addressService;

    public ProductProviderResponseDto getProductProvider(UUID id) {
        return ProductProviderMapper.toDto(providerRepository.findById(id).orElseThrow(
                () -> new ProductProviderNotFoundException("ProductProvider with id " + id + " not found")
        ));
    }

    public List<ProductProviderResponseDto> getAllProductProviders() {
        return providerRepository.findAll().stream().map(ProductProviderMapper::toDto).toList();
    }

    public ProductProviderResponseDto createProductProvider(ProductProviderRequestDto dto) {
        log.info("Creating product provider with name {}", dto.getName());
        ProductProvider provider = ProductProviderMapper.toEntity(dto);
        provider = providerRepository.save(provider);
        Address address = addressService.createAddress(dto.getAddress(), provider);
        provider.setAddress(address);
        return ProductProviderMapper.toDto(providerRepository.save(provider));
    }

    public ProductProviderResponseDto updateProductProvider(UUID id, ProductProviderRequestDto dto) {
        log.info("Updating product provider with id {}", id);
        ProductProvider provider = providerRepository.findById(id).orElseThrow(
                () -> new ProductProviderNotFoundException("ProductProvider with id " + id + " not found")
        );
        return ProductProviderMapper.toDto(providerRepository.save(ProductProviderMapper.update(provider, dto)));
    }

    public void deleteProductProvider(UUID id) {
        log.info("Deleting product provider with id {}", id);
        if (!providerRepository.existsById(id)) {
            throw new ProductProviderNotFoundException("ProductProvider with id " + id + " not found");
        }
        providerRepository.deleteById(id);
    }

    public ProductProvider getProductProviderEntity(UUID id) {
        return providerRepository.findById(id).orElseThrow(
                () -> new ProductProviderNotFoundException("ProductProvider with id " + id + " not found")
        );
    }

    public void linkInventoryItem(UUID providerId, InventoryItem inventoryItem) {
        ProductProvider provider = getProductProviderEntity(providerId);
        provider.getInventoryItems().add(inventoryItem);
        providerRepository.save(provider);
    }

    public void unlinkInventoryItem(UUID providerId, InventoryItem inventoryItem) {
        ProductProvider provider = getProductProviderEntity(providerId);
        provider.getInventoryItems().remove(inventoryItem);
        providerRepository.save(provider);
    }
}
