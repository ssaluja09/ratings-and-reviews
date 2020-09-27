package com.fbl.ecommerce.reviewsandratings.controller;

import com.fbl.ecommerce.reviewsandratings.exception.NotFoundException;
import com.fbl.ecommerce.reviewsandratings.model.Error;
import com.fbl.ecommerce.reviewsandratings.model.OffsetBasedPageRequest;
import com.fbl.ecommerce.reviewsandratings.model.ReviewDTO;
import com.fbl.ecommerce.reviewsandratings.model.RatingReviewListDTO;
import com.fbl.ecommerce.reviewsandratings.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {

    private static final int LIMIT_DEFAULT = 20;

    private static final int OFFSET_DEFAULT = 0;

    private static final String SORT_BY_DEFAULT = "highest rated";

    @Autowired
    ReviewService reviewService;

    @PostMapping("/ratingsAndReviews")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        Optional<ReviewDTO> dto = reviewService.addReview(reviewDTO);
        if (dto.isPresent())
            return new ResponseEntity(dto, HttpStatus.CREATED);
        else
            return new ResponseEntity(new Error("400", "Your review for this product already exists"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ratingsAndReviews/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable long reviewId) {
        try {
            return ResponseEntity.ok()
                    .body(reviewService.getReview(reviewId));

        } catch (NotFoundException e) {
            return new ResponseEntity(new Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/averageRatings")
    public ResponseEntity<?> getAverageRating(@RequestParam long entityId) {
        try {


            return ResponseEntity.ok()
                    .body(reviewService.getAverageRatingByProduct(entityId));

        } catch (NotFoundException e) {
            return new ResponseEntity(new Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reviews")
    public RatingReviewListDTO getAllReviews(@RequestParam long entityId,
                                             @RequestParam(required = false) Long offset,
                                             @RequestParam(required = false) Integer limit,
                                             @RequestParam(required = false) String sortBy) {


        Sort sortObj = decideSortingOrder(sortBy);
        long offsetValue = (offset == null) ? OFFSET_DEFAULT : offset.longValue();
        int limitValue = (limit == null) ? LIMIT_DEFAULT : limit.intValue();
        return reviewService.getAllReviewsByProduct(entityId, new OffsetBasedPageRequest(offsetValue, limitValue, sortObj));
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

        return "File Successfully Uploaded";
    }

    @GetMapping("/productList")
    public List<Long> getProductList(@RequestParam(required = false) Long offset,
                                     @RequestParam(required = false) Integer limit) {
        long offsetValue = (offset == null) ? OFFSET_DEFAULT : offset.longValue();
        int limitValue = (limit == null) ? LIMIT_DEFAULT : limit.intValue();
        return reviewService.getAllProducts(offsetValue, limitValue);
    }

    /*@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }*/

    private Sort decideSortingOrder(String sortBy) {
        Sort sortObj;
        switch (sortBy.toLowerCase()) {
            case "most recent": {
                sortObj = Sort.by("creationTime");
                break;
            }
            case "highest rated": {
                sortObj = Sort.by(Sort.Direction.DESC, "rating");
                break;
            }
            case "lowest rated": {
                sortObj = Sort.by(Sort.Direction.ASC, "rating");
                break;
            }
            case "productId": {
                sortObj = Sort.by(Sort.Direction.ASC, "productId");
                break;
            }
            default: {
                sortObj = Sort.by(Sort.Direction.DESC, "rating");
            }
        }
        return sortObj;
    }
}
