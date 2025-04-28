package br.com.jfood.ms_products.service;

import br.com.jfood.ms_products.dto.PageResponseDTO;
import br.com.jfood.ms_products.dto.ProductRequestDTO;
import br.com.jfood.ms_products.dto.ProductResponseDTO;
import br.com.jfood.ms_products.model.BaseResponse;
import br.com.jfood.ms_products.model.Product;
import br.com.jfood.ms_products.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ResponseEntity<Object> add(ProductRequestDTO dto) {
        if (dto == null) {
            var message = "Received ProductRequestDTO null.";
            logger.warn(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(message));
        }
        logger.info("Saving new product.");
        Product savedProduct = productRepository.save(buildProduct(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<ProductResponseDTO> findAll(Pageable pageable) {
        logger.info("Finding all products.");
        Page<Product> pageableProducts = productRepository.findAll(pageable);
        Page<ProductResponseDTO> dtoPage = pageableProducts.map(p -> {
            return new ProductResponseDTO(p.getId(), p.getDescription(), p.getPrice());
        });
        return new PageResponseDTO<ProductResponseDTO>(dtoPage);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findById(Long id) {
        if (id == null)
            return badRequest("Product id not informed");

        logger.info("Finding product by id: [{}].", id);
        Optional<Product> optionalProduct = findProductById(id);
        if (optionalProduct.isEmpty()) {
            logProductNotFound(id);
            return productNotFound();
        }
        Product foundProduct = optionalProduct.get();
        return ResponseEntity.ok(buildProductDTO(foundProduct));
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        if (id == null)
            return badRequest("Product id not informed");

        logger.info("Deleting product by id: [{}].", id);
        Optional<Product> optionalProduct = findProductById(id);
        if (optionalProduct.isEmpty()) {
            logProductNotFound(id);
            return productNotFound();
        }

        Product foundProduct = optionalProduct.get();
        productRepository.delete(foundProduct);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, ProductRequestDTO dto) {
        if (id == null || dto == null)
            return badRequest("Invalid request");

        logger.info("Updating product by id: [{}].", id);
        Optional<Product> optionalProduct = findProductById(id);
        if (optionalProduct.isEmpty()) {
            logProductNotFound(id);
            return productNotFound();
        }

        Product foundProduct = optionalProduct.get();
        if (dto.getDescription() != null)
            foundProduct.setDescription(dto.getDescription());
        if (dto.getPrice() != null)
            foundProduct.setPrice(dto.getPrice());

        return ResponseEntity.ok(foundProduct);
    }

    @Transactional(readOnly = true)
    private Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    private Product buildProduct(ProductRequestDTO dto) {
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }

    private ProductResponseDTO buildProductDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getDescription(),
                product.getPrice());
    }

    private ResponseEntity<Object> productNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse("Product not found."));
    }

    private void logProductNotFound(Long productId) {
        logger.info("Product not found ID: [{}].", productId);
    }

    private ResponseEntity<Object> badRequest(String message) {
        message = (message == null || message.isEmpty()) ? "Bad Request" : message;
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(message));
    }
}
