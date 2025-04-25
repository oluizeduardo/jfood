package br.com.jfood.ms_products.service;

import br.com.jfood.ms_products.dto.PageResponseDTO;
import br.com.jfood.ms_products.dto.ProductRequestDTO;
import br.com.jfood.ms_products.dto.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public void save(@Valid ProductRequestDTO productRequestDTO) {
    }

    public PageResponseDTO<ProductResponseDTO> findAll(Pageable pageable) {
        return null;
    }

    public ProductResponseDTO findProductById(Long id) {
        return null;
    }

    public void delete(ProductResponseDTO foundProduct) {
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        return null;
    }
}
