package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getReviews(int pageSize);

    int getTotalPagesForReview();

    void changeStatus(List<ReviewDTO> reviewDTOS);

    void deleteReview(Long id);

    void addReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewWhereShownIsTrue();
}
