package br.com.jfood.ms_products.controller;

import br.com.jfood.ms_products.dto.PageResponseDTO;
import br.com.jfood.ms_products.dto.ProductRequestDTO;
import br.com.jfood.ms_products.dto.ProductResponseDTO;
import br.com.jfood.ms_products.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Object> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.add(productRequestDTO);
    }

    @GetMapping
    public PageResponseDTO<ProductResponseDTO> findAllProducts(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.update(id, productRequestDTO);
    }

}
