package com.fbl.ecommerce.reviewsandratings.dao;

import com.fbl.ecommerce.reviewsandratings.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(long productId);

    List<Review> findAllByAuthorId(long authorId);
}
