package mf.ecommerce.auth_service.util;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import mf.ecommerce.auth_service.exception.JwtParsingException;
import mf.ecommerce.auth_service.kafka.AccountEvent;

public class JwtClaimsMapper {

    public static AccountEvent toEvent(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return AccountEvent.builder()
                    .id(claims.getStringClaim("sub"))
                    .email(claims.getStringClaim("email"))
                    .firstName(claims.getStringClaim("given_name"))
                    .lastName(claims.getStringClaim("family_name"))
                    .build();
        } catch (Exception e) {
            throw new JwtParsingException("Failed to parse jwt claims: " + e.getMessage());
        }
    }

}
