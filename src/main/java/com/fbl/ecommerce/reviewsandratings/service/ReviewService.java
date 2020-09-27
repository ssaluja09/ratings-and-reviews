package com.fbl.ecommerce.reviewsandratings.service;

import com.fbl.ecommerce.reviewsandratings.dao.ReviewRepository;
import com.fbl.ecommerce.reviewsandratings.exception.NotFoundException;
import com.fbl.ecommerce.reviewsandratings.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@Service
public class ReviewService {

    public static String TYPE = "text/csv";

    @Autowired
    ReviewRepository reviewRepository;

    public Optional<ReviewDTO> addReview(ReviewDTO reviewDTO) {

        Review review = new Review(reviewDTO);
        ReviewDTO responseReviewDTO = null;
        try {
            review = reviewRepository.save(review);
            responseReviewDTO = new ReviewDTO(review);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.ofNullable(responseReviewDTO);
        }
        return Optional.ofNullable(responseReviewDTO);
    }

    public ReviewDTO getReview(long reviewId) throws NotFoundException {

        Optional<Review> review = Optional.ofNullable(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found for id: " + reviewId)));

        ReviewDTO reviewDTO = new ReviewDTO(review.get());

        return reviewDTO;
    }

    public RatingReviewListDTO getAllReviewsByProduct(long productId, Pageable pageable) {
        RatingDTO rating = null;
        try {
            rating = getAverageRatingByProduct(productId);
        } catch (NotFoundException e) {
            return RatingReviewListDTO.of(rating, new ArrayList<>(0));
        }
        List<Review> reviewList = reviewRepository.findAllByProductId(productId, pageable);
        List<ReviewDTO> dtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDTO dto = new ReviewDTO(review);
            dtoList.add(dto);
        }
        RatingReviewListDTO ratingReviewListDTO = RatingReviewListDTO.of(rating, dtoList);
        return ratingReviewListDTO;
    }

    public List<ReviewDTO> getAllReviewsByUser(long authorId) {
        List<Review> reviewList = reviewRepository.findAllByAuthorId(authorId);
        List<ReviewDTO> dtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDTO dto = new ReviewDTO(review);
            dtoList.add(dto);
        }
        return dtoList;
    }

    //@Cacheable(key= "#productId")   -> evict every 10mins
    public RatingDTO getAverageRatingByProduct(long productId) throws NotFoundException {

        List<Review> reviewList = reviewRepository.findAllByProductId(productId);
        if (reviewList.size() == 0)
            throw new NotFoundException("Reviews for product with id " + productId + " not found");
        long count = 0, sumRatings = 0;
        for (Review review : reviewList) {
            sumRatings += review.getRating();
            count++;
        }
        double avgRating = (double) sumRatings / count;
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAvg = df.format(avgRating);
        RatingDTO ratingDTO = RatingDTO.builder()
                .averageRating(Double.parseDouble(formattedAvg))
                .totalNumberOfReviews(count)
                .build();

        return ratingDTO;
    }

    public void readFile(MultipartFile file) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<Review> reviewList = new ArrayList<>();
            for (CSVRecord record : csvParser.getRecords()) {
                Review review = new Review(
                        Long.parseLong(record.get("author id")),
                        Long.parseLong(record.get("Product ID")),
                        record.get("Title"),
                        record.get("Review Text"),
                        Integer.parseInt(record.get("Rating")));
                reviewList.add(review);
            }
            reviewRepository.saveAll(reviewList);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<Long> getAllProducts(long offsetValue, int limitValue) {
        return reviewRepository.findAllProductIds(offsetValue, limitValue);
    }
}
