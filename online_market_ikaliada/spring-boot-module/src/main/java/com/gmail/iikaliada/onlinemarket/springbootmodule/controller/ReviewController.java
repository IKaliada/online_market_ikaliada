package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.PaginationHandler;
import com.gmail.iikaliada.onlinemarket.springbootmodule.model.ReviewDTOList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ReviewController {

    private final PaginationHandler pagination;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService, PaginationHandler pagination) {
        this.reviewService = reviewService;
        this.pagination = pagination;
    }

    @GetMapping("/private/reviews")
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
        return "reviews";
    }

    @PostMapping("/private/reviews/changeStatus")
    public String changeStatus(@ModelAttribute("reviews") ReviewDTOList reviewDTOList) {
        reviewService.changeStatus(reviewDTOList.getReviewDTOList());
        return "redirect:/private/reviews";
    }

    @PostMapping("/private/reviews/{id}/delete")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return "redirect:/private/reviews";
    }

    @GetMapping("/private/review")
    public String getNewReviewPage(ReviewDTO reviewDTO, Model model) {
        model.addAttribute("review", reviewDTO);
        return "review";
    }

    @PostMapping("/private/review/add")
    public String addReview(@Valid @ModelAttribute("review") ReviewDTO reviewDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "review";
        }
        reviewService.addReview(reviewDTO);
        return "redirect:/articles";
    }
}
