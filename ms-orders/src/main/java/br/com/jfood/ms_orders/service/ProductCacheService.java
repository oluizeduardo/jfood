package br.com.jfood.ms_orders.service;

import br.com.jfood.ms_orders.dto.PageResponseDTO;
import br.com.jfood.ms_orders.dto.ProductResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCacheService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCacheService.class);

    private final RestTemplate restTemplate;
    private final String productListApiUrl;

    // When a thread changes the value of a variable, other threads see the change immediately.
    // Without volatile, the value may be cached locally to the thread, causing inconsistencies in concurrent readings.
    private static volatile List<ProductResponseDTO> cachedProducts = Collections.emptyList();

    public ProductCacheService(RestTemplate restTemplate, @Value("${product.api.list-url}") String productListApiUrl) {
        if (productListApiUrl == null || productListApiUrl.isEmpty()) {
            throw new IllegalArgumentException("Missing configuration: 'product.api.list-url' must be set.");
        }
        this.restTemplate = restTemplate;
        this.productListApiUrl = productListApiUrl;
    }

    @Scheduled(fixedDelayString = "#{${scheduler.products.refresh.minutes:60} * 60000}")
    public void fetchProducts() {
        try {
            logger.debug("Fetching product list from {}", productListApiUrl);

            ResponseEntity<PageResponseDTO<ProductResponseDTO>> response = restTemplate.exchange(
                    productListApiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PageResponseDTO<ProductResponseDTO>>() {
                    }
            );

            List<ProductResponseDTO> products = Optional.ofNullable(response.getBody())
                    .map(PageResponseDTO::getContent)
                    .orElse(Collections.emptyList());

            if (products.isEmpty()) {
                logger.warn("Fetched product list is empty.");
            } else {
                cachedProducts = products;
                logger.info("Product list updated. Total products: [{}].", products.size());
            }

        } catch (Exception e) {
            logger.error("Failed to fetch product list from {}: {}", productListApiUrl, e.getMessage());
        }
    }

    public static List<ProductResponseDTO> getCachedProducts() {
        return cachedProducts;
    }
}
