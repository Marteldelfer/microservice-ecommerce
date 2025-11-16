package mf.ecommerce.auth_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.auth_service.exception.JsonParsingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String accountValidationTopic;
    private final ObjectMapper objectMapper;

    public AccountEventProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${topics.account-creation-topic}") String accountValidationTopic,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.accountValidationTopic = accountValidationTopic;
        this.objectMapper = objectMapper;
    }

    private String toJSON(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e.getMessage());
        }
    }

    public void sendAccountCreationEvent(AccountEvent event) {
        log.info("Sending account creation event with id: {}", event.getId());
        log.info("Sending account creation event with event: {}", toJSON(event));
        kafkaTemplate.send(accountValidationTopic, toJSON(event));
    }
}
