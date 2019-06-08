package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForUiDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.PaginationHandler;
import com.gmail.iikaliada.onlinemarket.springbootmodule.model.ReviewDTOList;
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

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.ADMIN_AUTHORITY_CONSTANT;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.CUSTOMER_AUTHORITY_CONSTANT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ReviewService reviewService;
    @Mock
    private PaginationHandler pagination;

    @MockBean
    private UserForUiDTO userForUiDTO;

    @Before
    public void init() {
        ReviewController reviewController = new ReviewController(reviewService, pagination);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnReviewPage() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        reviewDTO.setText("some text");
        reviewDTO.setDate(new Date());
        reviewDTO.setShown(true);
        reviewDTO.setUserForUiDTO(userForUiDTO);
        List<ReviewDTO> reviews = Collections.singletonList(reviewDTO);
        ReviewDTOList reviewDTOList = new ReviewDTOList();
        reviewDTOList.setReviewDTOList(reviews);
        int pageSize = 1;
        when(reviewService.getReviews(pageSize)).thenReturn(reviews);
        this.mockMvc.perform(get("/private/reviews.html").requestAttr("page", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("reviews"));
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldRedirectToReviewPageWhenTextOfReviewShorterThan10Symbols() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText("test");
        this.mockMvc.perform(post("/private/review/add").flashAttr("review", reviewDTO))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("review"));
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldRedirectToReviewPageWhenTextOfReviewLongerThan200Symbols() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText("testaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.mockMvc.perform(post("/private/review/add").flashAttr("review", reviewDTO))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("review"));
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldRedirectToReviewPageWhenTextOfReviewContainsNotOnlyLatinLetter() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText("test тест самый настоящий");
        this.mockMvc.perform(post("/private/review/add").flashAttr("review", reviewDTO))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("review"));
    }
}
