package com.fbl.ecommerce.reviewsandratings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
/*@Table(name = "review", uniqueConstraints =
    @UniqueConstraint(columnNames = {"author_id", "product_id"})
)*/
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

    private Sentiment sentimentScore;
/*
    @Temporal(TemporalType.TIMESTAMP)
    Date creationTime;*/

    private String secondaryText;

    @Column(name = "creation_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationTime;

/*
    private boolean isProfane;*/

    public Review() {

    }

    public Review(ReviewDTO reviewDTO) {
        this.authorId = reviewDTO.getAuthor();
        this.productId = reviewDTO.getEntityId();
        this.title = reviewDTO.getTitle();
        this.reviewText = reviewDTO.getDescription();
        this.rating = reviewDTO.getRating();
        this.reviewStatus = StringUtils.isEmpty(reviewDTO.getReviewStatus()) ? ReviewStatus.IN_REVIEW : reviewDTO.getReviewStatus();
        this.creationTime = LocalDateTime.now();
    }

    public Review(long authorId, long productId, String title, String reviewText, int rating) {
        this.authorId = authorId;
        this.productId = productId;
        this.title = title;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewStatus = ReviewStatus.IN_REVIEW;
        this.creationTime = LocalDateTime.now();
    }
}


