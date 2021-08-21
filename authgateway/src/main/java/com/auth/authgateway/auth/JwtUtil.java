package com.auth.authgateway.auth;

import com.auth.authgateway.configuration.AuthGatewayProperties;
import com.auth.authgateway.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.jose4j.keys.RsaKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    @Autowired
    private AuthGatewayProperties gatewayProperties;

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Optional<UserDto> getUserDetailsFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        List<String> groupNames = claims.get("grp", ArrayList.class);
        final Set<String> groups = groupNames.stream().collect(Collectors.toSet());
        return Optional.ofNullable(new UserDto(claims.get("firstName", String.class),
                claims.get("lastName", String.class),
                claims.getSubject(),
                claims.get("language", String.class),
                claims.get("mail", String.class),
                claims.get("country", String.class),groups));
    }

    private Claims getAllClaimsFromToken(String token) {
        final PublicKey publicKey;
        try {
            publicKey = (new RsaKeyUtil()).fromPemEncoded(gatewayProperties.getPublicKey());
        } catch (InvalidKeySpecException | JoseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }
}
