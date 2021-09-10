package com.exercise.retail.service;

import com.exercise.retail.model.DiscountType;
import com.exercise.retail.model.UserType;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class DiscountMappingServiceImplTest {

    private DiscountMappingService discountMappingService = new DiscountMappingServiceImpl();

    @Test
    public void Should_Map_AffiliateUserType_To_AffiliateDiscountType() {
        assertEquals(DiscountType.AFFILIATE_DISCOUNT, discountMappingService.getDiscountByUserType(UserType.AFFILIATE));
    }

    @Test
    public void Should_Map_EmployeeUserType_To_EmployeeDiscountType() {
        assertEquals(DiscountType.EMPLOYEE_DISCOUNT, discountMappingService.getDiscountByUserType(UserType.EMPLOYEE));
    }

    @Test
    public void Should_Map_LoyalUserType_To_LoyalDiscountType() {
        assertEquals(DiscountType.LOYALTY_DISCOUNT, discountMappingService.getDiscountByUserType(UserType.LOYAL));
    }

    @Test
    public void Should_Map_RegularUserType_To_NoneDiscountType() {
        assertEquals(DiscountType.NONE, discountMappingService.getDiscountByUserType(UserType.REGULAR));
    }

    @Test
    public void Should_Map_NullUserType_To_NoneDiscountType() {
        assertEquals(DiscountType.NONE, discountMappingService.getDiscountByUserType(null));
    }

    @Test
    public void Should_MapEmployeeDiscount_To_EmployeeDiscountFunction() {
        Function<Double, Double> function = discountMappingService.getFunctionByDiscount(DiscountType.EMPLOYEE_DISCOUNT);
        assertEquals(3.0, function.apply(10.0), 0.001);
        assertEquals(0.0, function.apply(0.0), 0.001);
        assertEquals(3.333, function.apply(11.11), 0.001);
    }

    @Test
    public void Should_MapAffiliateDiscount_To_AffiliateDiscountFunction() {
        Function<Double, Double> function = discountMappingService.getFunctionByDiscount(DiscountType.AFFILIATE_DISCOUNT);
        assertEquals(1.0, function.apply(10.0), 0.001);
        assertEquals(0.0, function.apply(0.0), 0.001);
        assertEquals(1.111, function.apply(11.11), 0.001);
    }

    @Test
    public void Should_MapLoyalDiscount_To_LoyalDiscountFunction() {
        Function<Double, Double> function = discountMappingService.getFunctionByDiscount(DiscountType.LOYALTY_DISCOUNT);
        assertEquals(0.5, function.apply(10.0), 0.001);
        assertEquals(0.0, function.apply(0.0), 0.001);
        assertEquals(0.555, function.apply(11.11), 0.001);
    }

    @Test
    public void Should_MapNoneDiscount_To_NoneDiscountFunction() {
        Function<Double, Double> function = discountMappingService.getFunctionByDiscount(DiscountType.NONE);
        assertEquals(0.0, function.apply(10.0), 0.001);
        assertEquals(0.0, function.apply(0.0), 0.001);
        assertEquals(0.0, function.apply(11.11), 0.001);
    }

    @Test
    public void Should_MapNullDiscount_To_NoneDiscountFunction() {
        Function<Double, Double> function = discountMappingService.getFunctionByDiscount(null);
        assertEquals(0.0, function.apply(10.0), 0.001);
        assertEquals(0.0, function.apply(0.0), 0.001);
        assertEquals(0.0, function.apply(11.11), 0.001);
    }
}
