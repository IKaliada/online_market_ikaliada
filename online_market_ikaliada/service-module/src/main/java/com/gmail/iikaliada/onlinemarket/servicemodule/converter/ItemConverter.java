package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Item;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;

public interface ItemConverter {

    ItemDTO toItemDTO(Item item);

    Item fromItemDTO(ItemDTO itemDTO);
}
