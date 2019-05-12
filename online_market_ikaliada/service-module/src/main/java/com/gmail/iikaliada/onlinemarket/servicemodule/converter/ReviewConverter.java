package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;

public interface ReviewConverter {

    Review fromReviewDTO(ReviewDTO reviewDTO);

    ReviewDTO toReviewDTO(Review review);
}
