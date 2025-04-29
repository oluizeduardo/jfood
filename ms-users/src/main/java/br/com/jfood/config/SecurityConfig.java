package br.com.jfood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class that defines access rules and integrates JWT-based
 * authentication with Keycloak in a Spring Boot application.
 * <p>
 * This class uses Spring Security to protect API endpoints and enables method-level security.
 * It configures a stateless security filter chain with role-based access control.
 * </p>
 *
 * <p><strong>Key features:</strong></p>
 * <ul>
 *     <li>Enables method-level security via {@code @EnableMethodSecurity}</li>
 *     <li>Restricts access to user-related endpoints to users with the {@code MANAGER} role</li>
 *     <li>Integrates with Keycloak using JWT tokens</li>
 *     <li>Uses a custom converter to extract roles from the Keycloak JWT token</li>
 * </ul>
 *
 * @author luiz_costa
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String ROLE_MANAGER = "MANAGER";

    /**
     * Configures the Spring Security filter chain.
     * <p>
     * It enforces the following security rules:
     * <ul>
     *     <li>Only users with the {@code MANAGER} role can register, update, or delete users</li>
     *     <li>All other endpoints require authentication</li>
     *     <li>Authentication is done via OAuth2 JWT tokens</li>
     *     <li>Session management is stateless</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} instance provided by Spring Security
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").hasRole(ROLE_MANAGER)
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole(ROLE_MANAGER)
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole(ROLE_MANAGER)
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Provides a custom JWT authentication converter to extract user roles from
     * the Keycloak token.
     *
     * @return a {@link Converter} that converts {@link Jwt} into {@link AbstractAuthenticationToken}
     */
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverterImpl());
        return converter;
    }
}

