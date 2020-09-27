package com.fbl.ecommerce.reviewsandratings.dao;

import com.fbl.ecommerce.reviewsandratings.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(long productId, Pageable pageable);

    @Query("SELECT r FROM Review r" +
            " WHERE r.productId = :productId"/* +
            " LIMIT :limit"*/)
    List<Review> findAllByProductId(long productId);

    List<Review> findAllByAuthorId(long authorId);

    @Query(value = "SELECT * FROM Review" +
            " WHERE review_status = '0'" +
            " limit :limit ", nativeQuery = true)
    List<Review> findDrafts(@Param("limit") int limit);
}
