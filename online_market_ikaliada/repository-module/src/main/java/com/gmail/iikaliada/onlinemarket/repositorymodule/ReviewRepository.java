package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends ConnectionRepository {

    List<Review> getReview(Connection connection, int pageSize);

    int getTotalPagesForReview(Connection connection);

    void changeStatus(Connection connection, Long ids);

    void deleteReview(Connection connection, Long id);
}
