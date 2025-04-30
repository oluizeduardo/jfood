package br.com.jfood.ms_orders.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String description, BigDecimal price) {
}
