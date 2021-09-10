package com.exercise.retail.service;

import com.exercise.retail.model.DiscountType;
import com.exercise.retail.model.UserType;

import java.util.function.Function;

/**
 * Service that maps:
 *  - the UserType to the DiscountType
 *  - the DiscountType to a callback function, which calculates the percentage discount with complexity of 1
 */
public interface DiscountMappingService {

    DiscountType getDiscountByUserType(UserType userType);
    Function<Double, Double> getFunctionByDiscount(DiscountType discountType);
}
