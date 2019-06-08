package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;

import java.util.List;

public interface ReviewRepository extends GenericRepository<Long, Review> {
    List<Review> getReviewWhereShownTrue();
}
