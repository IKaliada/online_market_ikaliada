package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.UserRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsers(int offset, int limit) {
        String getUserQuery = "from " + entityClass.getName() + " u where u.isDeleted = 0 order by email asc";
        Query query = entityManager.createQuery(getUserQuery)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        String userDeleteQuery = "update " + entityClass.getName() + " c set c.isDeleted = 1 where c.id =:id";
        Query query = entityManager.createQuery(userDeleteQuery);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public User getUsersByUsername(String email) {
        String userByUsernameQuery = "from " + entityClass.getName() + " c where c.email =:email";
        Query query = entityManager.createQuery(userByUsernameQuery);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
