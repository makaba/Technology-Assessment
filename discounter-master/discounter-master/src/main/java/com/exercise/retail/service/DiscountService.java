package com.exercise.retail.service;

import com.exercise.retail.model.DiscountInfo;
import com.exercise.retail.model.PaymentInfo;

/**
 * Service, which calculates a discount for a payment based on the payment properties.
 */
public interface DiscountService {

    /**
     * Calculates a discount based on a PaymentInfo.
     */
    DiscountInfo calculateDiscount(PaymentInfo paymentInfo);
}
