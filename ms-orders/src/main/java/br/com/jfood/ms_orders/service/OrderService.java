package br.com.jfood.ms_orders.service;

import br.com.jfood.ms_orders.dto.*;
import br.com.jfood.ms_orders.model.Order;
import br.com.jfood.ms_orders.model.OrderItem;
import br.com.jfood.ms_orders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ProductPriceService productPriceService;

    public OrderService(OrderRepository orderRepository, ProductPriceService productPriceService) {
        this.orderRepository = orderRepository;
        this.productPriceService = productPriceService;
    }

    @Transactional
    public ResponseEntity<Object> add(PurchaseOrderRequestDTO dto, String userId) {
        if (dto == null) {
            var message = "Received PurchaseOrderRequestDTO null.";
            logger.warn(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(message));
        }
        logger.info("User [{}] is creating an order", userId);
        Order savedOrder = orderRepository.save(buildOrder(dto, userId));
        PurchaseOrderResponseDTO response = buildOrderDTO(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<PurchaseOrderResponseDTO> findAll(Pageable pageable) {
        logger.info("Finding all orders.");

        Page<Order> pageableOrders = orderRepository.findAll(pageable);
        Page<PurchaseOrderResponseDTO> dtoPage = pageableOrders.map(order -> {

            // Mapeia cada OrderItem para uma lista de OrderItemResponseDTO.
            List<OrderItemResponseDTO> itemDTOs = PurchaseOrderResponseDTO.fromOrderItems(order.getItems());

            return new PurchaseOrderResponseDTO(
                    order.getId(),
                    order.getKeycloakUserId(),
                    order.getPurchaseDate(),
                    order.getTotalAmount(),
                    itemDTOs
            );
        });
        return new PageResponseDTO<PurchaseOrderResponseDTO>(dtoPage);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findById(Long id) {
        if (id == null)
            return badRequest("Order id not informed");

        logger.info("Finding order by id: [{}].", id);
        Optional<Order> optionalOrder = findOrderById(id);
        if (optionalOrder.isEmpty()) {
            logOrderNotFound(id);
            return orderNotFound();
        }
        Order foundOrder = optionalOrder.get();
        return ResponseEntity.ok(buildOrderDTO(foundOrder));
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        if (id == null)
            return badRequest("Order id not informed");

        logger.info("Deleting order by id: [{}].", id);
        Optional<Order> optionalOrder = findOrderById(id);
        if (optionalOrder.isEmpty()) {
            logOrderNotFound(id);
            return orderNotFound();
        }

        Order foundOrder = optionalOrder.get();
        orderRepository.delete(foundOrder);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, PurchaseOrderRequestDTO dto) {
        if (id == null || dto == null)
            return badRequest("Invalid request");

        logger.info("Updating order by id: [{}].", id);
        Optional<Order> optionalOrder = findOrderById(id);
        if (optionalOrder.isEmpty()) {
            logOrderNotFound(id);
            return orderNotFound();
        }

        Order existingOrder = optionalOrder.get();

        // Atualiza os itens do pedido
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<OrderItem> updatedItems = dto.getItems()
                    .stream()
                    .map(itemDTO -> {
                        OrderItem item = new OrderItem();
                        item.setProductId(itemDTO.getProductId());
                        item.setQuantity(itemDTO.getQuantity());
                        // TODO: buscar o preço no serviço ms-products.
                        item.setPrice(new BigDecimal(0));
                        item.setOrder(existingOrder);
                        return item;
                    })
                    .toList();

            // Apaga do banco de dados a lista de produtos.
            existingOrder.getItems().clear();
            // Salva uma nova lista de produtos.
            existingOrder.getItems().addAll(updatedItems);
            existingOrder.setTotalAmount(calculateTotalAmount(updatedItems));
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return ResponseEntity.ok(buildOrderDTO(updatedOrder));
    }

    @Transactional(readOnly = true)
    private Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    private Order buildOrder(PurchaseOrderRequestDTO dto, String keycloakUserId) {
        if (dto == null)
            throw new IllegalArgumentException("PurchaseOrderRequestDTO cannot be null");

        Order order = new Order();
        order.setKeycloakUserId(keycloakUserId);
        order.setPurchaseDate(LocalDateTime.now());

        // Convertendo a lista de itens do DTO para uma lista de OrderItem
        List<OrderItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    OrderItem item = new OrderItem();
                    var productId = itemDto.getProductId();
                    item.setProductId(productId);
                    item.setQuantity(itemDto.getQuantity());
                    item.setPrice(getProductPrice(productId));
                    item.setOrder(order);
                    return item;
                })
                .toList();

        order.setItems(items);
        order.setTotalAmount(calculateTotalAmount(items));
        return order;
    }

    private BigDecimal getProductPrice(Long productId) {
        if (productId == null)
            return BigDecimal.ZERO;
        return productPriceService.searchProductPrice(productId);
    }

    public static BigDecimal calculateTotalAmount(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PurchaseOrderResponseDTO buildOrderDTO(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null");

        PurchaseOrderResponseDTO orderResponse = new PurchaseOrderResponseDTO();
        orderResponse.setOrderId(order.getId());
        orderResponse.setKeycloakUserId(order.getKeycloakUserId());
        orderResponse.setPurchaseDate(order.getPurchaseDate());

        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(orderItem -> {
                    OrderItemResponseDTO item = new OrderItemResponseDTO();
                    item.setProductId(orderItem.getProductId());
                    item.setQuantity(orderItem.getQuantity());
                    item.setPrice(orderItem.getPrice());
                    return item;
                })
                .toList();

        orderResponse.setItems(items);
        orderResponse.setTotalAmount(order.getTotalAmount());
        return orderResponse;
    }

    private ResponseEntity<Object> orderNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse("Order not found."));
    }

    private void logOrderNotFound(Long productId) {
        logger.info("Order not found ID: [{}].", productId);
    }

    private ResponseEntity<Object> badRequest(String message) {
        message = (message == null || message.isEmpty()) ? "Bad Request" : message;
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(message));
    }
}
