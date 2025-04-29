package br.com.jfood.ms_orders.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

/**
 * Custom JWT converter that extracts roles and groups from a Keycloak token and maps them
 * to Spring Security {@link GrantedAuthority} instances.
 * <p>
 * This implementation looks for roles in the "realm_access.roles" claim and optionally
 * for group names in the "groups" claim. All extracted values are prefixed with {@code ROLE_}
 * to align with Spring Security’s role naming convention.
 * </p>
 *
 * <p>Example role from Keycloak token:</p>
 * <pre>
 * {
 *   "realm_access": {
 *     "roles": ["MANAGER", "USER"]
 *   },
 *   "groups": ["/admin", "/user"]
 * }
 * </pre>
 *
 * <p>Converted authorities:</p>
 * <pre>
 * ROLE_MANAGER, ROLE_USER, ROLE_/admin, ROLE_/user
 * </pre>
 *
 * This converter is typically used in conjunction with {@link JwtAuthenticationConverter}
 * to integrate with Spring Security’s OAuth2 resource server support.
 */
public class KeycloakRealmRoleConverterImpl implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String GROUPS_CLAIM = "groups";
    public static final String ROLES_CLAIM = "roles";
    public static final String ROLES_PREFIX = "ROLE_";
    public static final String REALM_ACCESS_CLAIM = "realm_access";

    /**
     * Extracts and converts roles and groups from the JWT into a collection of granted authorities.
     *
     * @param jwt the incoming JSON Web Token
     * @return a collection of {@link GrantedAuthority} derived from roles and groups
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (Objects.nonNull(jwt.getClaims().get(REALM_ACCESS_CLAIM)) &&
                jwt.getClaims().get(REALM_ACCESS_CLAIM) instanceof Map<?, ?> realmAccess &&
                Objects.nonNull(realmAccess.get(ROLES_CLAIM)) &&
                realmAccess.get(ROLES_CLAIM) instanceof List<?> roles) {
            grantedAuthorities.addAll(this.converterRoles(roles));
        }

        if (Objects.nonNull(jwt.getClaims().get(GROUPS_CLAIM)) &&
                jwt.getClaims().get(GROUPS_CLAIM) instanceof Collection<?> roles) {
            grantedAuthorities.addAll(this.converterRoles(roles));
        }

        return grantedAuthorities;
    }

    /**
     * Converts a collection of roles or group names into {@link SimpleGrantedAuthority} instances,
     * adding the required {@code ROLE_} prefix.
     *
     * @param roles collection of raw role or group strings
     * @return a list of granted authorities with the prefix applied
     */
    private List<SimpleGrantedAuthority> converterRoles(Collection<?> roles) {
        return roles.stream()
                .map(String::valueOf)
                .map(ROLES_PREFIX::concat)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
