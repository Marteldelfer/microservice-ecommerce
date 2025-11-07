package mf.ecommerce.account_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.account_service.exception.JsonParsingException;
import mf.ecommerce.account_service.mapper.AccountMapper;
import mf.ecommerce.account_service.model.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String accountValidationTopic;
    private final ObjectMapper objectMapper;

    public AccountEventProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${topics.account-validation-topic}") String accountValidationTopic,
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

    public void sendValidationEmailEvent(Account account, String jwt) {
        log.info("Sending account created event with id: {}", account.getId());
        AccountEmailEvent event = AccountMapper.toEmailEvent(account, jwt);
        kafkaTemplate.send(accountValidationTopic, account.getId().toString(), toJSON(event));
    }

}
