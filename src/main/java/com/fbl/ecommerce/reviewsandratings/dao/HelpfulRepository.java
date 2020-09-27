package com.fbl.ecommerce.reviewsandratings.dao;

import com.fbl.ecommerce.reviewsandratings.model.Helpfulness;
import com.fbl.ecommerce.reviewsandratings.model.HelpfulnessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpfulRepository extends JpaRepository<Helpfulness, HelpfulnessId> {

    @Query("SELECT COUNT(h) FROM Helpfulness h" +
            " WHERE h.id.reviewId = :reviewId" +
            " AND isHelpful = :isHelpful")
    int findHelpfulCount(@Param("reviewId") long reviewId,
                         @Param("isHelpful") boolean isHelpful);
}
