package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private long reviewId;

    private long author;

    private long entityId;

    private String title;

    private String description;

    private int rating;

    private ReviewStatus reviewStatus;

    public ReviewDTO(Review review) {
        this.reviewId = review.getId();
        this.author = review.getAuthorId();
        this.entityId = review.getProductId();
        this.title = review.getTitle();
        this.description = review.getReviewText();
        this.rating = review.getRating();
        this.reviewStatus = review.getReviewStatus();
    }
}
