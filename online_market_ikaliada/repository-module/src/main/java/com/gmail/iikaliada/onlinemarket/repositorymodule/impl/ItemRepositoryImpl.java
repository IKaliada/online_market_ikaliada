package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ItemRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {
    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getItems(Integer offset, Integer limit) {
        String getItemQuery = "from " + entityClass.getName() + " i order by i.name asc";
        Query query = entityManager.createQuery(getItemQuery)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return query.getResultList();
    }
}
