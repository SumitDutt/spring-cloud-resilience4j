package com.microservice.review.service.impl;


import com.microservice.review.bean.Review;
import com.microservice.review.entity.ReviewEntity;
import com.microservice.review.repository.ReviewRepository;
import com.microservice.review.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;


    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }

    @Override
    public boolean reviewDeleteById(Long id) {

        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        if (reviewEntity != null) {
            reviewRepository.delete(reviewEntity);
            return true;
        }
        return false;


    }

    @Override
    public boolean updateReview(Long id, Review review) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        if (reviewEntity != null) {
            reviewEntity.setRating(review.getRating());
            reviewEntity.setDescription(review.getDescription());
            reviewEntity.setTitle(review.getTitle());
            reviewRepository.save(reviewEntity);
            return true;
        }

        return false;
    }

    @Override
    public Review reviewById(Long id) {

        return convertReviewEntityToReiew(reviewRepository.findById(id).orElse(null));

    }

    @Override
    public ReviewEntity addReview(Long companyId, Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setCompanyId(companyId);
        reviewEntity.setDescription(review.getDescription());
        reviewEntity.setRating(review.getRating());
        reviewEntity.setTitle(review.getTitle());

        return reviewRepository.save(reviewEntity);
    }


    private ReviewEntity convertReviewToReviewEntity(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(review.getId());
        reviewEntity.setDescription(review.getDescription());
        reviewEntity.setTitle(review.getTitle());
        reviewEntity.setRating(review.getRating());
        return reviewEntity;
    }

    @Override
    public List<Review> findAll(Long companyId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findByCompanyId(companyId);
        return reviewEntityList
                .stream()
                .map(this::convertReviewEntityToReiew)
                .collect(Collectors.toList());
    }

    private Review convertReviewEntityToReiew(ReviewEntity reviewEntity) {
        if (reviewEntity != null) {
            Review review = new Review();
            review.setId(reviewEntity.getId());
            review.setDescription(reviewEntity.getDescription());
            review.setTitle(reviewEntity.getTitle());
            review.setRating(reviewEntity.getRating());
            review.setCompanyId(reviewEntity.getCompanyId());
            return review;
        }
        return null;
    }
}
