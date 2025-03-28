package br.com.jfood.controller;

import br.com.jfood.dto.MenuItemDTO;
import br.com.jfood.mapper.MenuItemMapper;
import br.com.jfood.model.MessageResponse;
import br.com.jfood.model.MenuItem;
import br.com.jfood.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.findAll();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable UUID id) {
        return menuItemService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> createMenuItem(@PathVariable UUID userId, @RequestBody MenuItemDTO menuItemDTO) {
        try {
            menuItemService.save(userId, menuItemMapper.toMenuItem(menuItemDTO));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMenuItem(@PathVariable UUID id) {
        if (menuItemService.findById(id).isPresent()) {
            menuItemService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Menu item not found."));
    }
}
