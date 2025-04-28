package br.com.jfood.ms_products.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {

    private Long id;
    private String description;
    private BigDecimal price;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String description, BigDecimal price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
