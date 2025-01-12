package com.microservice.review.service;


import com.microservice.review.bean.Review;
import com.microservice.review.entity.ReviewEntity;

import java.util.List;

public interface ReviewService {

    boolean reviewDeleteById(Long id);

    boolean updateReview(Long id, Review review);

    Review reviewById( Long id);

    ReviewEntity addReview(Long companyId, Review review);

    List<Review> findAll(Long companyId);
}
