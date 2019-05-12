package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/private/users/review")
    public String getComments(
            Model model,
            @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        model.addAttribute("currentPage", currentPage);
        List<ReviewDTO> reviews = reviewService.getReviews(currentPage);
        int amountPage = reviewService.getTotalPagesForReview();
        model.addAttribute("reviews", reviews);
        model.addAttribute("amountPage", amountPage);
        if (currentPage > 1) {
            int previousPage = currentPage - 1;
            model.addAttribute("previousPage", previousPage);
        }
        if (currentPage < amountPage) {
            int nextPage = currentPage + 1;
            model.addAttribute("nextPage", nextPage);
        }
        int[] pages = new int[amountPage];
        for (int i = 0; i < amountPage; i++) {
            pages[i] = i + 1;
        }
        model.addAttribute("pages", pages);
        return "review";
    }

    @PostMapping("/private/users/review/changeStatus")
    public String changeStatus(@Nullable @RequestParam("ids") Long[] ids) {
        List<Long> collect = null;
        if (ids != null) {
            collect = Arrays.stream(ids).collect(Collectors.toList());
            for (Long id : collect) {
                reviewService.changeStatus(id);
            }
        }
        for (Long id : collect) {
            reviewService.changeStatus1(id);
        }
        return "redirect:/private/users/review";
    }

    @PostMapping("/private/users/review/{id}")
    public String changePassword(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return "redirect:/private/users/review";
    }
}
