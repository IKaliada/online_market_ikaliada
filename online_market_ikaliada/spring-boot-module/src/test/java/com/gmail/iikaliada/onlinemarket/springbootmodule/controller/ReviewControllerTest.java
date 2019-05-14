package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForReviewDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.gmail.iikaliada.onlinemarket.springbootmodule.constant.AuthoritiesConstants.ADMIN_AUTHORITY_CONSTANT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {
    private MockMvc mockMvc;
    @Mock
    private ReviewService reviewService;

    @MockBean
    private UserForReviewDTO userForReviewDTO;

    @Before
    public void init() {
        ReviewController reviewController = new ReviewController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnCommentPage() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO(1L, "some text", new Date(), true, userForReviewDTO);
        List<ReviewDTO> reviews = Collections.singletonList(reviewDTO);
        int pageSize = 1;
        when(reviewService.getReviews(pageSize)).thenReturn(reviews);
        this.mockMvc.perform(get("/private/review.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("reviews", reviews))
                .andExpect(forwardedUrl("review"));
    }
}
