package br.com.jfood.ms_products.controller;

import br.com.jfood.ms_products.dto.PageResponseDTO;
import br.com.jfood.ms_products.dto.ProductRequestDTO;
import br.com.jfood.ms_products.dto.ProductResponseDTO;
import br.com.jfood.ms_products.model.BaseResponse;
import br.com.jfood.ms_products.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Object> registerProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        try {
            productService.save(productRequestDTO);
        } catch (Exception e) {
            logger.warn("BAD REQUEST - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public PageResponseDTO<ProductResponseDTO> getAllProducts(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        ProductResponseDTO foundProduct = productService.findProductById(id);
        if (foundProduct == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("Product not found."));
        }
        return ResponseEntity.ok(foundProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        ProductResponseDTO foundProduct = productService.findProductById(id);
        if (foundProduct == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("Product not found."));
        }
        productService.delete(foundProduct);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
        if (updatedProduct == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("Product not found."));
        }
        return ResponseEntity.ok(updatedProduct);
    }

}
