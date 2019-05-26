package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ReviewRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {
}
