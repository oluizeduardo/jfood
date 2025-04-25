package br.com.jfood.ms_products.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotBlank(message = "Description can not be blank.")
    private String description;

    @NotNull(message = "The price can not be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Value must be greater than zero")
    @Digits(integer = 6, fraction = 2, message = "Maximum of 6 whole digits and 2 decimal places")
    private BigDecimal price;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String description, BigDecimal price) {
        this.description = description;
        this.price = price;
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
