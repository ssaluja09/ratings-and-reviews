package com.fbl.ecommerce.reviewsandratings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "review", uniqueConstraints =
    @UniqueConstraint(columnNames = {"author_id", "product_id"})
)
@Data
/*@Builder(toBuilder = true)*/
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "author_id")
    private long authorId;

    @Column(name = "product_id")
    private long productId;

    private String title;

    private String reviewText;

    private int rating;

    private ReviewStatus reviewStatus;

    /*public static Review of(ReviewDTO reviewDTO) {
        return Review.builder()
                .authorId(reviewDTO.getAuthor())
                .productId(reviewDTO.getEntityId())
                .title(reviewDTO.getTitle())
                .reviewText(reviewDTO.getDescription())
                .rating(reviewDTO.getRating())
                .reviewStatus(StringUtils.isEmpty(reviewDTO.getReviewStatus()) ? ReviewStatus.IN_REVIEW : reviewDTO.getReviewStatus())
                .build();
    }*/

    public Review() {

    }

    public Review(ReviewDTO reviewDTO) {
        this.authorId = reviewDTO.getAuthor();
        this.productId = reviewDTO.getEntityId();
        this.rating = reviewDTO.getRating();
        this.reviewText = reviewDTO.getDescription();
        this.title = reviewDTO.getTitle();
        this.reviewStatus = StringUtils.isEmpty(reviewDTO.getReviewStatus()) ? ReviewStatus.IN_REVIEW : reviewDTO.getReviewStatus();
    }

    public Review(long authorId, long productId, String title, String reviewText, int rating) {
        this.authorId = authorId;
        this.productId = productId;
        this.title = title;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewStatus = ReviewStatus.IN_REVIEW;
    }
}


