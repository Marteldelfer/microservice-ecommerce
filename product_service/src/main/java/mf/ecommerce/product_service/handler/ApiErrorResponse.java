package mf.ecommerce.product_service.handler;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class ApiErrorResponse {

    private Instant timestamp;
    private int code;
    private String status;

    private List<String> errors;
}
