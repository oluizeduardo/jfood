package br.com.jfood.ms_products.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String description, BigDecimal price) {
}
