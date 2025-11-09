package mf.ecommerce.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RoleValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<RoleValidationGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(RoleValidationGatewayFilterFactory.class);
    private final WebClient webClient;

    public RoleValidationGatewayFilterFactory(
            @Value("${auth.service.url:default}") String authServiceUrl
    ) {
        if (authServiceUrl.equals("default")) {
            log.warn("No auth service url provided");
            authServiceUrl = "http://default";
        }
        super(Config.class);
        log.info("Auth service url: {}", authServiceUrl);
        this.webClient = WebClient.builder().baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null) {
                return unauthorized(exchange);
            }
            log.info("Sending validation request to {}", "/auth/validate/" + config.getRole().toLowerCase());
            return webClient.get()
                    .uri("/auth/validate/" + config.getRole().toLowerCase())
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange))
                    .onErrorResume(_ -> unauthorized(exchange));
        };
    }

    private static Mono<Void> unauthorized(ServerWebExchange exchange) {
        log.info("Request unauthorized");
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    public static class Config {
        String role;

        public String getRole() {
            return role;
        }
    }
}
