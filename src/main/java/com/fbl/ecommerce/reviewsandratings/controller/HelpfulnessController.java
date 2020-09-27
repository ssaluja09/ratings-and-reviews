package com.fbl.ecommerce.reviewsandratings.controller;

import com.fbl.ecommerce.reviewsandratings.model.Error;
import com.fbl.ecommerce.reviewsandratings.model.HelpfulCountDTO;
import com.fbl.ecommerce.reviewsandratings.service.HelpfulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class HelpfulnessController {

    private static final int LIMIT_DEFAULT = 20;

    private static final int OFFSET_DEFAULT = 0;

    private static final String SORT_BY_DEFAULT = "highest rated";

    @Autowired
    HelpfulService helpfulService;

    @PutMapping("/helpfulReview")
    public ResponseEntity<?> addReview(@RequestParam long reviewId, @RequestParam long author, @RequestParam boolean isHelpful) {
        boolean isCreated = helpfulService.applyHelpfulness(reviewId, author, isHelpful);
        if (isCreated)
            return new ResponseEntity(HttpStatus.CREATED);
        else
            return new ResponseEntity(new Error("500", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/helpfulReview/{reviewId}")
    public ResponseEntity<?> addReview(@PathVariable long reviewId) {
        Optional<HelpfulCountDTO> helpfulCountDTO = helpfulService.getHelpfulCount(reviewId);
        if (helpfulCountDTO.isPresent()) {
            return new ResponseEntity(helpfulCountDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Error("404", "Review not found"), HttpStatus.NOT_FOUND);
        }

    }

}
