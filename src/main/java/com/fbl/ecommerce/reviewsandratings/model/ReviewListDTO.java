package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ReviewListDTO {

    private List<ReviewDTO> reviewList;

    public static ReviewListDTO of(RatingDTO rating, List<ReviewDTO> list) {
        return ReviewListDTO.builder()
                .reviewList(list)
                .build();
    }

}
