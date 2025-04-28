package br.com.jfood.orders_ms.dto;

import br.com.jfood.orders_ms.model.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderResponseDTO {

    private Long orderId;
    private Long userId;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> items;

    public PurchaseOrderResponseDTO() {
    }

    public PurchaseOrderResponseDTO(Long orderId, Long userId, LocalDateTime purchaseDate, BigDecimal totalAmount, List<OrderItemResponseDTO> items) {
        this.orderId = orderId;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
