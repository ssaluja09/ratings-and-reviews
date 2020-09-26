package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RatingDTO {

    private long totalNumberOfReviews;

    private double averageRating;

}
