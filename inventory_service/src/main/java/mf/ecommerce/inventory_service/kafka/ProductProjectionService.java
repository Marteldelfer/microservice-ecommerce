package mf.ecommerce.inventory_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.inventory_service.exception.InvalidEventTypeException;
import mf.ecommerce.inventory_service.exception.JsonParsingException;
import mf.ecommerce.inventory_service.mapper.ProductProjectionMapper;
import mf.ecommerce.inventory_service.model.ProductProjection;
import mf.ecommerce.inventory_service.repository.ProductProjectionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductProjectionService {

    private final ProductProjectionRepository productProjectionRepository;
    private final ObjectMapper objectMapper;

    private ProductEvent parseJson(String event) {
        try {
            return objectMapper.readValue(event, ProductEvent.class);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("Could not parse ProductEvent into ProductProjection");
        }
    }

    @Async
    @KafkaListener(topics = "product-update", groupId = "inventory-service")
    public void productUpdateConsumer(@Payload String event) {
        ProductEvent productEvent = parseJson(event);
        switch (productEvent.getType()) {
            case CREATED -> createProductProjection(ProductProjectionMapper.toProductProjection(productEvent));
            case UPDATED -> updateProductProjection(ProductProjectionMapper.toProductProjection(productEvent));
            case DELETED -> deleteProductProjection(productEvent.getId());
            default -> throw new InvalidEventTypeException("Unknown event type: " + productEvent.getType());
        }
    }

    private void createProductProjection(ProductProjection projection) {
        if (productProjectionRepository.existsById(projection.getId())) {
            log.warn("Product projection with id {} already exists", projection.getId());
            updateProductProjection(projection);
            return;
        }
        log.info("Creating product projection with id {}", projection.getId());
        productProjectionRepository.save(projection);
    }

    private void updateProductProjection(ProductProjection projection) {
        if (!productProjectionRepository.existsById(projection.getId())) {
            log.warn("Product projection with id {} does not exist", projection.getId());
        }
        log.info("Update product projection with id {}", projection.getId());
        productProjectionRepository.save(projection);
    }

    private void deleteProductProjection(UUID id) {
        log.info("Delete product projection with id {}", id);
        productProjectionRepository.deleteById(id);
    }
}
