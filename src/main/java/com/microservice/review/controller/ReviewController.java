package com.microservice.review.controller;


import com.microservice.review.bean.Review;
import com.microservice.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> findAll(@RequestParam Long companyId) {
        return reviewService.findAll(companyId);
    }

    @PostMapping
    public ResponseEntity<String> addJob(@RequestParam Long companyId,@RequestBody Review review) {
        if (reviewService.addReview(companyId,review) != null)
            return new ResponseEntity<>("Add Review Succefully", HttpStatus.CREATED);
        return new ResponseEntity<>("Company Id Not Found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getJobById(@PathVariable Long id) {
        Review review = reviewService.reviewById(id);
        if (review != null)
            return new ResponseEntity<>(review, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //@RequestMapping(value = "/jobs/{id}",method =RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateJob(@PathVariable Long id, @RequestBody Review review) {
        if (reviewService.updateReview(id, review))
            return new ResponseEntity<>("Review Updated Succefully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
        if (reviewService.reviewDeleteById(id))
            return new ResponseEntity<>("Review Deleted Succefully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
