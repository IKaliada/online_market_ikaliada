package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ItemRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Item;
import com.gmail.iikaliada.onlinemarket.servicemodule.ItemService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ItemConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    @Transactional
    public List<ItemDTO> getItems(Integer currentPage) {
        int offset = ((LIMIT * currentPage));
        currentPage = LIMIT * (currentPage - 1);
        List<Item> items = itemRepository.getItems(currentPage, offset);
        return items.stream()
                .map(itemConverter::toItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id);
        return itemConverter.toItemDTO(item);
    }

    @Override
    @Transactional
    public void add(ItemDTO itemDTO){
        itemDTO.setUniqueNumber(UUID.randomUUID().toString());
        itemRepository.persist(itemConverter.fromItemDTO(itemDTO));
    }

    @Override
    @Transactional
    public void deleteItemById(Long id) {
        itemRepository.removeById(id);
    }

    @Override
    @Transactional
    public void deleteItemsById(List<Long> ids) {
        for (Long id : ids) {
            itemRepository.removeById(id);
        }
    }

    @Override
    @Transactional
    public void copyItemById(Long id){
        Item item = itemRepository.findById(id);
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setDescription(item.getDescription());
        add(itemDTO);
    }

    @Override
    @Transactional
    public int getTotalPages() {
        int totalEntities = itemRepository.getCountOfEntities();
        return getPagesNumber(totalEntities);
    }

    private int getPagesNumber(int totalEntities) {
        int pagesNumber = totalEntities / LIMIT;
        if (totalEntities % LIMIT > 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }
}
