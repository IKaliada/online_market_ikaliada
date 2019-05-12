package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ReviewConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {
    private UserConverter userConverter;

    public ReviewConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public Review fromReviewDTO(ReviewDTO reviewDTO) {
        Review comment = new Review();
        comment.setId(reviewDTO.getId());
        comment.setText(reviewDTO.getText());
        comment.setDate(reviewDTO.getDate());
        comment.setShown(reviewDTO.isShown());
        comment.setUserForReview(userConverter.fromUserForCommentDTO(reviewDTO.getUserForReviewDTO()));
        return comment;
    }

    @Override
    public ReviewDTO toReviewDTO(Review review) {
        ReviewDTO commentDTO = new ReviewDTO();
        commentDTO.setId(review.getId());
        commentDTO.setText(review.getText());
        commentDTO.setDate(review.getDate());
        commentDTO.setShown(review.isShown());
        commentDTO.setUserForReviewDTO(userConverter.toUserForCommentDTO(review.getUserForReview()));
        return commentDTO;
    }
}
