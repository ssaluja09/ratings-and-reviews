package com.fbl.ecommerce.reviewsandratings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Error {

    private String code;
    private String message;



}
