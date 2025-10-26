package mf.ecommerce.product_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.exception.JsonParsingExeption;
import mf.ecommerce.product_service.mapper.ProductMapper;
import mf.ecommerce.product_service.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String updateTopic;
    private final ObjectMapper objectMapper;

    public ProductEventProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${topics.product-update}") String productUpdateTopic,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.updateTopic = productUpdateTopic;
        this.objectMapper = objectMapper;
    }

    private String toJSON(ProductEvent productEvent) {
        try {
            return objectMapper.writeValueAsString(productEvent);
        } catch (JsonProcessingException e) {
            throw new JsonParsingExeption(e.getMessage());
        }
    }

    public void sendProductCreatedEvent(Product product) {
        log.info("Sending product created event with id {}", product.getId());
        ProductEvent productEvent = ProductMapper.toEvent(product, ProductEventType.CREATED);
        kafkaTemplate.send(updateTopic, productEvent.getId().toString(), toJSON(productEvent));
    }

    public void sendProductUpdatedEvent(Product product) {
        log.info("Sending product updated event with id {}", product.getId());
        ProductEvent productEvent = ProductMapper.toEvent(product, ProductEventType.UPDATED);
        kafkaTemplate.send(updateTopic, productEvent.getId().toString(), toJSON(productEvent));
    }

    public void sendProductDeletedEvent(Product product) {
        log.info("Sending product deleted event with id {}", product.getId());
        ProductEvent productEvent = ProductMapper.toEvent(product, ProductEventType.DELETED);
        kafkaTemplate.send(updateTopic, productEvent.getId().toString(), toJSON(productEvent));
    }
}
