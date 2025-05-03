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

@Service
public class ProductClientService {

    private static final Logger logger = LoggerFactory.getLogger(ProductClientService.class);

    private final RestTemplate restTemplate;
    private final String productApiUrl;

    public ProductClientService(RestTemplate restTemplate,
                                @Value("${product.api.url}") String productApiUrl) {

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
                var message = "Price not found for product with ID [" + productId + "].";
                logger.warn(message);
                throw new RuntimeException(message);
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP client error while requesting product with ID {}: [{}] - {}",
                    productId,
                    e.getStatusCode().value(),
                    e.getResponseBodyAsString());
            throw e;
        }
    }

    public BigDecimal fallbackGetPrice(Long productId, Throwable throwable) {
        logger.error("Unable to retrieve price for product [{}]. Product service is unavailable. Using fallback mechanism.", productId, throwable);

        return ProductCacheService.getCachedProducts().stream()
                .filter(product -> product.id().equals(productId))
                .findFirst()
                .map(ProductResponseDTO::price)
                .orElseGet(() -> {
                    logger.warn("Product [{}] not found in cache. Returning default fallback price.", productId);
                    return BigDecimal.ONE;
                });
    }


}

