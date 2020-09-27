package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewSentimentListDTO {

    private List<ReviewSentimentDTO> data;

}
