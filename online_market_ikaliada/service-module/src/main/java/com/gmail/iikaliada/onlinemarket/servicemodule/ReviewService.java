package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getReviews(int pageSize);

    int getTotalPagesForReview();

    void changeStatus(Long ids);

    void changeStatus1(Long collect);

    void deleteReview(Long id);
}
