package com.exercise.retail.rest;

import com.exercise.retail.DiscountBase;
import com.exercise.retail.DiscounterApplication;
import com.exercise.retail.exception.IllegalPaymentInfoException;
import com.exercise.retail.model.*;
import com.exercise.retail.service.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        RestService.class,
        DiscountService.class,
        DiscountMappingService.class,
        DiscounterApplication.class
})
public class RestServiceTest extends DiscountBase {

    @Autowired
    private RestService restService;

    @Test
    public void Should_Calculate_Discount_For_Employee_Without_Groceries() {
        withGroceries(false);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        ResponseEntity<DiscountInfo> discountInfoResponse = restService.calculateDiscount(paymentInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discountInfo = discountInfoResponse.getBody();
        assertCalculated(150.45, 50.135, DiscountType.EMPLOYEE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_Affiliate_Without_Groceries() {
        withGroceries(false);
        withAmount(280.0);
        withUserType(UserType.AFFILIATE);
        ResponseEntity<DiscountInfo> discountInfoResponse = restService.calculateDiscount(paymentInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discountInfo = discountInfoResponse.getBody();
        assertCalculated(280.0, 38.0, DiscountType.AFFILIATE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_LoyalUser_Without_Groceries() {
        withGroceries(false);
        withAmount(380.0);
        withUserType(UserType.LOYAL);
        ResponseEntity<DiscountInfo> discountInfoResponse = restService.calculateDiscount(paymentInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discountInfo = discountInfoResponse.getBody();
        assertCalculated(380.0, 34.0, DiscountType.LOYALTY_DISCOUNT);
    }

    @Test
    public void Should_NotCalculate_Discount_For_RegularUser_With_Groceries() {
        withGroceries(true);
        withAmount(380.0);
        withUserType(UserType.REGULAR);
        ResponseEntity<DiscountInfo> discountInfoResponse = restService.calculateDiscount(paymentInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discountInfo = discountInfoResponse.getBody();
        assertCalculated(380.0, 15.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Employee_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        ResponseEntity<DiscountInfo> discountInfoResponse = restService.calculateDiscount(paymentInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discountInfo = discountInfoResponse.getBody();
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Affiliate_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.AFFILIATE);
        ResponseEntity<DiscountInfo> discountInfoResponse = restService.calculateDiscount(paymentInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discountInfo = discountInfoResponse.getBody();
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_LoyalUser_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.LOYAL);
        ResponseEntity<DiscountInfo> discountInfoResponse = restService.calculateDiscount(paymentInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discountInfo = discountInfoResponse.getBody();
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test(expected = IllegalPaymentInfoException.class)
    public void Should_Throw_Exception_When_PaymentInfo_Is_Null() {
        paymentInfo = null;
        restService.calculateDiscount(paymentInfo);
    }

    @Test(expected = IllegalPaymentInfoException.class)
    public void Should_Throw_Exception_When_UserInfo_Is_Null() {
        paymentInfo.setUserInfo(null);
        restService.calculateDiscount(paymentInfo);
    }

    @Test(expected = IllegalPaymentInfoException.class)
    public void Should_Throw_Exception_When_Amount_In_Negative() {
        paymentInfo.setAmount(-134.14);
        restService.calculateDiscount(paymentInfo);
    }

    @Test(expected = IllegalPaymentInfoException.class)
    public void Should_Throw_Exception_When_Amount_In_Null() {
        paymentInfo.setAmount(null);
        restService.calculateDiscount(paymentInfo);
    }
}
