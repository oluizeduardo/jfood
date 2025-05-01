package br.com.jfood.ms_orders.service;

import br.com.jfood.ms_orders.dto.ProductResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

/**
 * Service responsible for retrieving product pricing information from the {@code ms-products} microservice.
 * <p>
 * This service uses a {@link RestTemplate} to send HTTP GET requests to the external product service,
 * retrieving product details by ID and extracting the current price.
 * <p>
 * If the product is not found or an error occurs during the request, the service logs the issue
 * and returns {@code BigDecimal.ZERO} as a fallback.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * BigDecimal price = productPriceService.searchProductPrice(1L);
 * }</pre>
 */
@Service
public class ProductClientService {

    private static final Logger logger = LoggerFactory.getLogger(ProductClientService.class);

    private final RestTemplate restTemplate;
    private final String productApiUrl;

    public ProductClientService(RestTemplate restTemplate, @Value("${product.api.url}") String productApiUrl) {
        if (productApiUrl == null || productApiUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing configuration: 'product.api.url' must be set in application.properties.");
        }
        this.restTemplate = restTemplate;
        this.productApiUrl = productApiUrl;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetPrice")
    public BigDecimal getProductPrice(Long productId) {
        if (productId == null) {
            logger.warn("ProductId not informed - Impossible to get the product price.");
            return BigDecimal.ZERO;
        }

        try {
            ProductResponseDTO product = restTemplate.getForObject(productApiUrl, ProductResponseDTO.class, productId);
            if (product != null && product.price() != null) {
                return product.price();
            } else {
                logger.warn("Price not found for product with ID {}", productId);
            }
        } catch (HttpClientErrorException e) {
            logger.error("Error searching for product with ID {}: {}", productId, e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal fallbackGetPrice(Long productId, Throwable throwable) {
        logger.error("Impossible to get the price of product [{}]. Product service is unavailable - returning default price.", productId);
        return BigDecimal.ONE;
    }
}

