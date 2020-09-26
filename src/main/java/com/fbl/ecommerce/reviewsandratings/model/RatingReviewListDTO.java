package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class RatingReviewListDTO {

    private long totalNumberOfReviews;

    private double averageRating;

    private List<ReviewDTO> reviewList;

    public static RatingReviewListDTO of(RatingDTO rating, List<ReviewDTO> list) {

        if (rating == null) {
            return RatingReviewListDTO.builder()
                    .reviewList(list)
                    .averageRating(0)
                    .totalNumberOfReviews(0)
                    .build();
        }
        return RatingReviewListDTO.builder()
                .reviewList(list)
                .averageRating(rating.getAverageRating())
                .totalNumberOfReviews(rating.getTotalNumberOfReviews())
                .build();
    }

}
