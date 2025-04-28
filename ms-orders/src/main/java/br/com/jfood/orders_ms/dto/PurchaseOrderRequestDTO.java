package br.com.jfood.orders_ms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PurchaseOrderRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequestDTO> items;

    public PurchaseOrderRequestDTO() {
    }

    public PurchaseOrderRequestDTO(Long userId, List<OrderItemRequestDTO> items) {
        this.userId = userId;
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDTO> items) {
        this.items = items;
    }
}

