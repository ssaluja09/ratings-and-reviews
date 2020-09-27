package com.fbl.ecommerce.reviewsandratings.service;

import com.fbl.ecommerce.reviewsandratings.dao.HelpfulRepository;
import com.fbl.ecommerce.reviewsandratings.model.HelpfulCountDTO;
import com.fbl.ecommerce.reviewsandratings.model.Helpfulness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class HelpfulService {

    @Autowired
    HelpfulRepository helpfulRepository;

    public boolean applyHelpfulness(long reviewId, long author, boolean isHelpful) {
        Helpfulness helpfulness = new Helpfulness(author, reviewId, isHelpful);
        try {
            helpfulRepository.save(helpfulness);
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    //@Cacheable()
    public Optional<HelpfulCountDTO> getHelpfulCount(long reviewId) {
        HelpfulCountDTO countDTO;
        try {
            int helpfulCount = helpfulRepository.findHelpfulCount(reviewId, true);
            int notHelpfulCount = helpfulRepository.findHelpfulCount(reviewId, false);
            countDTO = new HelpfulCountDTO(helpfulCount, notHelpfulCount);
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
        return Optional.of(countDTO);
    }
}
