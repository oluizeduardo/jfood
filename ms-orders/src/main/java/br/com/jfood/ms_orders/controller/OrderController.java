package br.com.jfood.ms_orders.controller;

import br.com.jfood.ms_orders.dto.PageResponseDTO;
import br.com.jfood.ms_orders.dto.PurchaseOrderRequestDTO;
import br.com.jfood.ms_orders.dto.PurchaseOrderResponseDTO;
import br.com.jfood.ms_orders.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Object> addOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderDTO) {
        return orderService.add(purchaseOrderDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public PageResponseDTO<PurchaseOrderResponseDTO> findAllOrders(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        return orderService.delete(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @Valid @RequestBody PurchaseOrderRequestDTO productRequestDTO) {
        return orderService.update(id, productRequestDTO);
    }

}
