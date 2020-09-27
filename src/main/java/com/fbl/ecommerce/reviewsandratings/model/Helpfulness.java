package com.fbl.ecommerce.reviewsandratings.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
public class Helpfulness {

    @EmbeddedId
    private HelpfulnessId id;

    private boolean isHelpful;

    public Helpfulness(long authorId, long reviewId, boolean isHelpful) {
        this.id = new HelpfulnessId(authorId, reviewId);
        this.isHelpful = isHelpful;
    }
}
