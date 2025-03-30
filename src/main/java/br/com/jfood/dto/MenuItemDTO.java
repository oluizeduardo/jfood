package br.com.jfood.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

public class MenuItemDTO {

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 6, fraction = 2, message = "The price must have up to 6 digits in total, with up to 2 after the comma.")
    private double price;

    public MenuItemDTO(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
