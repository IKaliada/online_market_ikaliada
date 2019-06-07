package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ReviewRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {
    @Override
    @SuppressWarnings("unchecked")
    public List<Review> getReviewWhereShownTrue() {
        String showReviewQuery = "from " + entityClass.getName() + " where isShown = true";
        Query q = entityManager.createQuery(showReviewQuery);
        return q.getResultList();
    }
}
