package com.fbl.ecommerce.reviewsandratings.service;

import com.fbl.ecommerce.reviewsandratings.client.SentimentClient;
import com.fbl.ecommerce.reviewsandratings.dao.ReviewRepository;
import com.fbl.ecommerce.reviewsandratings.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class SentimentAndProfanityScheduler {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    SentimentClient sentimentClient;

    @Value("${scheduler.batchSize}")
    int batchSize;

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void startScheduler() {

        log.info("Starting Scheduler");

        List<Review> reviewList = reviewRepository.findDrafts(batchSize);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        HashMap<Long, Review> mapOfReviews = new HashMap<>();
        for (Review review : reviewList) {
            ReviewDTO reviewDTO = new ReviewDTO(review);
            reviewDTOList.add(reviewDTO);
            mapOfReviews.put(review.getId(), review);
        }

        ReviewListDTO listDTO = ReviewListDTO.of(reviewDTOList);
        ReviewSentimentListDTO responseDto = sentimentClient.getSentiments(listDTO);

        for (ReviewSentimentDTO sentimentDTO : responseDto.getData()) {
            if (mapOfReviews.containsKey(sentimentDTO.getId())) {
                Review review = mapOfReviews.get(sentimentDTO.getId());

                if (sentimentDTO.isProfane())   //  Adding the masked text to another column
                    review.setSecondaryText(sentimentDTO.getDescription());

                if (sentimentDTO.getScore() > 0.0) {
                    review.setSentimentScore(Sentiment.POSITIVE);
                } else if (sentimentDTO.getScore() < 0.0) {
                    review.setSentimentScore(Sentiment.NEGATIVE);
                } else {
                    review.setSentimentScore(Sentiment.NEUTRAL);
                }

                /*if (sentimentDTO.isProfane())
                    review.setReviewStatus(ReviewStatus.REJECTED);
                else*/
                review.setReviewStatus(ReviewStatus.PUBLISHED);

            }
        }
        reviewRepository.saveAll(reviewList);
    }
}
