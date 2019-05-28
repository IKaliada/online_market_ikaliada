package com.gmail.iikaliada.onlinemarket.springbootmodule.model;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import java.util.ArrayList;
import java.util.List;

public class ReviewDTOList {

    private List<ReviewDTO> reviewDTOList = new ArrayList<>();

    public List<ReviewDTO> getReviewDTOList() {
        return reviewDTOList;
    }

    public void setReviewDTOList(List<ReviewDTO> reviewDTOList) {
        this.reviewDTOList = reviewDTOList;
    }
}
