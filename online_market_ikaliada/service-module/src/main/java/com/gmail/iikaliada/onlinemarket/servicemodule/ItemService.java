package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getItems(Integer pageSize);

    ItemDTO getItemById(Long id);

    void add(ItemDTO itemDTO);

    void deleteItemById(Long id);

    int getTotalPages();

    void deleteItemsById(List<Long> ids);

    void copyItemById(Long id);
}
