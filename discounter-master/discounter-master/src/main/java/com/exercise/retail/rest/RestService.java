package com.exercise.retail.rest;

import com.exercise.retail.model.DiscountInfo;
import com.exercise.retail.model.PaymentInfo;
import com.exercise.retail.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest service responsible for calculating the discount for a payment depending on the user and payment information
 */
@RestController
@RequestMapping("/api/discount")
public class RestService {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    public ResponseEntity<DiscountInfo> calculateDiscount(@RequestBody PaymentInfo paymentInfo) {
        DiscountInfo discountInfo = discountService.calculateDiscount(paymentInfo);
        return new ResponseEntity<>(discountInfo, HttpStatus.OK);
    }
}
