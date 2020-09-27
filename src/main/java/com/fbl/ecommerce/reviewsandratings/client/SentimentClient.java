package com.fbl.ecommerce.reviewsandratings.client;

import com.fbl.ecommerce.reviewsandratings.model.ReviewDTO;
import com.fbl.ecommerce.reviewsandratings.model.ReviewListDTO;
import com.fbl.ecommerce.reviewsandratings.model.ReviewSentimentDTO;
import com.fbl.ecommerce.reviewsandratings.model.ReviewSentimentListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "EmployeeSearch", url = "http://localhost:4000/")
public interface SentimentClient {

    @PostMapping("/api/v1.0/nlp")
    public ReviewSentimentListDTO getSentiments(ReviewListDTO reviewListDTO);

}
