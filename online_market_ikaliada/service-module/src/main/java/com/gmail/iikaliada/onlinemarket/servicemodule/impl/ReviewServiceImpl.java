package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ReviewRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ReviewConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final static Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    @Transactional
    public List<ReviewDTO> getReviews(int currentPage) {
        int offset = ((LIMIT * currentPage));
        currentPage = LIMIT * (currentPage - 1);
        List<Review> users = reviewRepository.findAll(currentPage, offset);
        return users.stream()
                .map(reviewConverter::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int getTotalPagesForReview() {
        int totalEntities = reviewRepository.getCountOfEntities();
        return getPageNumber(totalEntities);
    }

    @Override
    @Transactional
    public void changeStatus(List<ReviewDTO> reviews) {

        for (ReviewDTO reviewDTO : reviews) {
            Review review = reviewRepository.findById(reviewDTO.getId());
            review.setShown(reviewDTO.getShown());
            reviewRepository.merge(review);
        }
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.removeById(id);
    }

    private int getPageNumber(int totalEntities) {
        int pagesNumber = totalEntities / LIMIT;
        if (totalEntities % LIMIT > 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }
}
