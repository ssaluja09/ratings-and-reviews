package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ReviewListDTO {

    private List<ReviewDTO> reviews;

    public static ReviewListDTO of(List<ReviewDTO> list) {
        return ReviewListDTO.builder()
                .reviews(list)
                .build();
    }

}
