package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class HelpfulnessId implements Serializable {

    private long authorId;

    private long reviewId;

    public HelpfulnessId(long authorId, long reviewId) {
        this.authorId = authorId;
        this.reviewId = reviewId;
    }
}
