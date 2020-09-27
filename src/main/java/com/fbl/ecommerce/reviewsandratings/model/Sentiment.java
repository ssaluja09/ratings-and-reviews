package com.fbl.ecommerce.reviewsandratings.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Sentiment {

    @JsonProperty("Positive")
    POSITIVE,
    @JsonProperty("Neutral")
    NEUTRAL,
    @JsonProperty("Negative")
    NEGATIVE;

    public String value() {
        return name().toLowerCase();
    }

}
