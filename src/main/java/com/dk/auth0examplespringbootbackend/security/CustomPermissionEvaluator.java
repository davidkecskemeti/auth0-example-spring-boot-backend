package com.dk.auth0examplespringbootbackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static String ANY = "ANY";
    private static String ALL = "ALL";

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {

        if (permission == null) return true;
        if (auth == null) return false;

        ObjectMapper objectMapper =
                new ObjectMapper().registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Map<String, Object> authCredentials = objectMapper.convertValue(auth.getCredentials(), Map.class);
        if (authCredentials == null) return false;
        Map<String, Object> authClaims = (Map<String, Object>) authCredentials.get("claims");
        if (authClaims == null) return false;
        List<String> authPermissions = (List<String>) authClaims.get("permissions");
        if (authPermissions == null) return false;

        if (permission instanceof String) {
            return authPermissions.contains((String) permission);
        } else if (permission instanceof List) {
            if (targetDomainObject == null) return true;

            if (!((String) targetDomainObject).equalsIgnoreCase("ANY") && !((String) targetDomainObject).equalsIgnoreCase("ALL")) {
                return false;
            }

            List<String> requestedPermissions = (List<String>) permission;

            if (((String) targetDomainObject).equalsIgnoreCase(ANY)) {
               return requestedPermissions.stream().anyMatch(authPermissions::contains);
            } else if (((String) targetDomainObject).equalsIgnoreCase(ALL)) {
                return new HashSet<>(requestedPermissions).containsAll(authPermissions);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(),
                permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().startsWith(targetType) &&
                    grantedAuth.getAuthority().contains(permission)) {
                return true;
            }
        }
        return false;
    }
}