package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ReviewRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ReviewConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.AuthenticatedUserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;
    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewConverter reviewConverter,
                             UserService userService) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
        this.userService = userService;
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

    @Override
    @Transactional
    public void addReview(ReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AuthenticatedUserDTO authenticatedUserDTO = userService.getAuthenticatedUser(currentPrincipalName);
        Review review = new Review();
        review.setDate(new Date());
        review.setText(reviewDTO.getText());
        User user = new User();
        user.setId(authenticatedUserDTO.getId());
        review.setUser(user);
        reviewRepository.persist(review);
    }

    @Override
    public List<ReviewDTO> getReviewWhereShownIsTrue() {
        List<Review> reviews = reviewRepository.getReviewWhereShownTrue();
        return reviews.stream().map(reviewConverter::toReviewDTO).collect(Collectors.toList());
    }

    private int getPageNumber(int totalEntities) {
        int pagesNumber = totalEntities / LIMIT;
        if (totalEntities % LIMIT > 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }
}
