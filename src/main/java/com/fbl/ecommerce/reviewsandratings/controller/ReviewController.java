package com.fbl.ecommerce.reviewsandratings.controller;

import com.fbl.ecommerce.reviewsandratings.exception.NotFoundException;
import com.fbl.ecommerce.reviewsandratings.model.Error;
import com.fbl.ecommerce.reviewsandratings.model.ReviewDTO;
import com.fbl.ecommerce.reviewsandratings.model.RatingReviewListDTO;
import com.fbl.ecommerce.reviewsandratings.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/ratingsAndReviews")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        Optional<ReviewDTO> dto = reviewService.addReview(reviewDTO);
        if(dto.isPresent())
            return new ResponseEntity(dto, HttpStatus.CREATED);
        else
            return new ResponseEntity(new Error("500", "Your review for this product already exists"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ratingsAndReviews/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable long reviewId) {
        try {
            return ResponseEntity.ok()
                    .body(reviewService.getReview(reviewId));

        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/averageRatings")
    public ResponseEntity<?> getAverageRating(@RequestParam long entityId) {
        try {
            return ResponseEntity.ok()
                    .body(reviewService.getAverageRatingByProduct(entityId));

        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reviews")
    public RatingReviewListDTO getAllReviews(@RequestParam long entityId) {
        return reviewService.getAllReviewsByProduct(entityId);
    }

    @GetMapping("/myReviews")
    public List<ReviewDTO> getAllReviewsByUser(@RequestParam long author) {
        return reviewService.getAllReviewsByUser(author);
    }

    @PostMapping("/uploadCsv")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        reviewService.readFile(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    /*@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }*/

}
