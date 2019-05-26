package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ItemService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private")
public class ItemApiController {

    private final ItemService itemService;

    @Autowired
    public ItemApiController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public List<ItemDTO> getItems(@RequestParam(value = "page", defaultValue = "1") Integer pageSize) {
        return itemService.getItems(pageSize);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<String> getItemById(@PathVariable("id") Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        return new ResponseEntity<>(itemDTO.toString(), HttpStatus.OK);
    }

    @PostMapping("/items/item")
    public ResponseEntity<String> addItem(@RequestBody ItemDTO itemDTO) {
        itemService.add(itemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/items/item/{id}")
    public ResponseEntity<String> addItem(@PathVariable(name = "id") Long id) {
        itemService.deleteItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
