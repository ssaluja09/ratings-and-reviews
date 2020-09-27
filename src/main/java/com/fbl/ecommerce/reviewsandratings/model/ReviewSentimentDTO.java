package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ReviewSentimentDTO {

    private long id;

    private boolean isProfane;

    private String description;

    private double score;

}
