package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ItemService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/private")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getItems(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        model.addAttribute("currentPage", currentPage);
        List<ItemDTO> items = itemService.getItems(currentPage);
        model.addAttribute("items", items);
        int totalPage = itemService.getTotalPages();
        model.addAttribute("totalPage", totalPage);
        if (currentPage > 1) {
            int previousPage = currentPage - 1;
            model.addAttribute("previousPage", previousPage);
        }
        if (currentPage < totalPage) {
            int nextPage = currentPage + 1;
            model.addAttribute("nextPage", nextPage);
        }
        int[] pages = new int[totalPage];
        for (int i = 0; i < totalPage; i++) {
            pages[i] = i + 1;
        }
        model.addAttribute("pages", pages);
        return "items";
    }

    @PostMapping("/items/delete")
    public String deleteItemsById(@Nullable @RequestParam("ids") List<Long> ids, Model model,
                                  @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        if (ids == null) {
            return "redirect:/private/items";
        }
        itemService.deleteItemsById(ids);
        getItems(model, currentPage);
        return "redirect:/private/items";
    }

    @GetMapping("/items/{id}")
    public String showItemById(@PathVariable("id") Long id, Model model) {
        ItemDTO itemDTO = itemService.getItemById(id);
        model.addAttribute("item", itemDTO);
        return "item";
    }

    @GetMapping("/items/{id}/copy")
    public String copyItemById(@PathVariable("id") Long id) {
        itemService.copyItemById(id);
        return "redirect:/private/items";
    }
}
