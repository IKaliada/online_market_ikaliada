package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> getItems(Integer currentPage, Integer offset);
}
