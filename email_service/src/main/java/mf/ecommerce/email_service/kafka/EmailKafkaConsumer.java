package mf.ecommerce.email_service.kafka;

import lombok.AllArgsConstructor;
import mf.ecommerce.email_service.service.EmailService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailKafkaConsumer {

    private final EmailService emailService;

    /* TODO @KafkaListeners
        Implement email templates
     */
}
