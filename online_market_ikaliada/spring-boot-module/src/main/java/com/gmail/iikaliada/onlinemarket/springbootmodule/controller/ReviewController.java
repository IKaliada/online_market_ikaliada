package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.model.ReviewDTOList;
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
        int size = 10;
        model.addAttribute("size", size);
        model.addAttribute("reviews", reviewDTOList);
        model.addAttribute("totalPage", totalPage);
        if (currentPage > 1) {
            int previousPage = currentPage - 1;
            model.addAttribute("previousPage", previousPage);
        }
        if (currentPage < totalPage) {
            int nextPage = currentPage + 1;
            model.addAttribute("nextPage", nextPage);
        }
        int[] pages = new int[totalPage];
        for (int i = 0; i < totalPage; i++) {
            pages[i] = i + 1;
        }
        model.addAttribute("pages", pages);
        return "review";
    }

    @PostMapping("/private/review/changeStatus")
    public String changeStatus(@ModelAttribute("reviewDTOList") ReviewDTOList reviewDTOList) {
        reviewService.changeStatus(reviewDTOList.getReviewDTOList());
        return "redirect:/private/review";
    }

    @PostMapping("/private/review/{id}")
    public String changePassword(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return "redirect:/private/review";
    }
}
