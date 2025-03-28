package br.com.jfood.dto;

public class MenuItemDTO {
    private String description;
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
