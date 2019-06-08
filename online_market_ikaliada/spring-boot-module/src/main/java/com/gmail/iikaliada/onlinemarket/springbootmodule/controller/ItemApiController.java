package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ItemService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.CustomException;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.ItemNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public ItemDTO getItemById(@PathVariable("id") Long id) {
        try {
            return itemService.getItemById(id);
        } catch (Exception e) {
            throw new ItemNotfoundException(id);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<String> addItem(@Valid @RequestBody ItemDTO itemDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.toString(),
                    "Seems like you entered not correct values. Try again!");
        }
        itemService.add(itemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable(name = "id") Long id) {
        itemService.deleteItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
