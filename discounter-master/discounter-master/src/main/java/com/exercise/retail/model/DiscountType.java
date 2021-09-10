package com.exercise.retail.model;

public enum DiscountType {

    /**
     * Discount for employees
     */
    EMPLOYEE_DISCOUNT,

    /**
     * Discount for affiliate user.
     */
    AFFILIATE_DISCOUNT,

    /**
     * Discount for user that has been customer for more that 2 years.
     */
    LOYALTY_DISCOUNT,

    /**
     * No discount applies.
     */
    NONE
}
