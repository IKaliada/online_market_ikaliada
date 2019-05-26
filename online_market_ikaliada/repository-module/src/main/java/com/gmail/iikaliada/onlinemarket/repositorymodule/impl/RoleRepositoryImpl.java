package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.RoleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        String query = "from " + entityClass.getName();
        Query q = entityManager.createQuery(query);
        return q.getResultList();
    }
}

