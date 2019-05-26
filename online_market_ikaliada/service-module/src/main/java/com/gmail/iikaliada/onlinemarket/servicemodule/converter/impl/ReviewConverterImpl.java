package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ReviewConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {

    private final UserConverter userConverter;

    public ReviewConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setText(review.getText());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setShown(review.isShown());
        reviewDTO.setUserForUiDTO(userConverter.toUserForUiDTO(review.getUser()));
        return reviewDTO;
    }
}
