package com.fbl.ecommerce.reviewsandratings.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelpfulCountDTO {

    private int helpfulCount;

    private int notHelpfulCount;

}
