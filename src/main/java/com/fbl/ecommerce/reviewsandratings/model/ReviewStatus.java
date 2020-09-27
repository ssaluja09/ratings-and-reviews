package com.fbl.ecommerce.reviewsandratings.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReviewStatus {
    @JsonEnumDefaultValue
    @JsonProperty("In Review")
    IN_REVIEW,
    @JsonProperty("Published")
    PUBLISHED,
    @JsonProperty("Rejected")
    REJECTED;
}
