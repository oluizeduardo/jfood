package br.com.jfood.ms_orders.dto;

import br.com.jfood.ms_orders.model.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderResponseDTO {

    private Long orderId;
    private String keycloakUserId;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> items;

    public PurchaseOrderResponseDTO() {
    }

    public PurchaseOrderResponseDTO(Long orderId, String keycloakUserId, LocalDateTime purchaseDate, BigDecimal totalAmount, List<OrderItemResponseDTO> items) {
        this.orderId = orderId;
        this.keycloakUserId = keycloakUserId;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getKeycloakUserId() {
        return keycloakUserId;
    }

    public void setKeycloakUserId(String keycloakUserId) {
        this.keycloakUserId = keycloakUserId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDTO> items) {
        this.items = items;
    }

    public static List<OrderItemResponseDTO> fromOrderItems(List<OrderItem> items) {
        return items.stream().map(item -> {
            OrderItemResponseDTO dto = new OrderItemResponseDTO();
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            return dto;
        }).toList();
    }

}
