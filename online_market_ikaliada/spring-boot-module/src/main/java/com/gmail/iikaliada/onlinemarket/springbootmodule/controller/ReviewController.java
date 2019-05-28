package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.PaginationHandler;
import com.gmail.iikaliada.onlinemarket.springbootmodule.model.ReviewDTOList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private PaginationHandler pagination;
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/private/review")
    public String getComments(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        model.addAttribute("currentPage", currentPage);
        List<ReviewDTO> reviews = reviewService.getReviews(currentPage);
        int totalPage = reviewService.getTotalPagesForReview();
        ReviewDTOList reviewDTOList = new ReviewDTOList();
        reviewDTOList.setReviewDTOList(reviews);
        model.addAttribute("reviews", reviewDTOList);
        pagination.getPagination(currentPage, model, totalPage);
        return "review";
    }

    @PostMapping("/private/review/changeStatus")
    public String changeStatus(@ModelAttribute("reviews") ReviewDTOList reviewDTOList) {
        reviewService.changeStatus(reviewDTOList.getReviewDTOList());
        return "redirect:/private/review";
    }

    @PostMapping("/private/review/{id}/delete")
    public String changePassword(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return "redirect:/private/review";
    }
}
