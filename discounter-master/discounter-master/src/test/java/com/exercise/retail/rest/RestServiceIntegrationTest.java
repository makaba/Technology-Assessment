package com.exercise.retail.rest;

import com.exercise.retail.DiscountBase;
import com.exercise.retail.DiscounterApplication;
import com.exercise.retail.model.*;
import com.exercise.retail.service.DiscountMappingService;
import com.exercise.retail.service.DiscountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        RestService.class,
        DiscountService.class,
        DiscountMappingService.class,
        DiscounterApplication.class
})
@WebAppConfiguration
public class RestServiceIntegrationTest extends DiscountBase {

    private static final String PATH = "/api/discount";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void Should_Calculate_Discount_For_Employee_Without_Groceries() throws Exception {
        withAmount(234.50);
        withGroceries(false);
        withUserType(UserType.EMPLOYEE);
        makeRequest();
        assertCalculated(234.50, 80.35, DiscountType.EMPLOYEE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_Employee_With_Groceries() throws Exception {
        withAmount(234.50);
        withGroceries(true);
        withUserType(UserType.EMPLOYEE);
        makeRequest();
        assertCalculated(234.50, 10.0, DiscountType.NONE);
    }

    @Test
    public void Should_Calculate_Discount_For_Affiliate_Without_Groceries() throws Exception {
        withAmount(234.50);
        withGroceries(false);
        withUserType(UserType.AFFILIATE);
        makeRequest();
        assertCalculated(234.50, 33.45, DiscountType.AFFILIATE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_Affiliate_With_Groceries() throws Exception {
        withAmount(234.50);
        withGroceries(true);
        withUserType(UserType.AFFILIATE);
        makeRequest();
        assertCalculated(234.50, 10.0, DiscountType.NONE);
    }

    @Test
    public void Should_Calculate_Discount_For_Loyal_Without_Groceries() throws Exception {
        withAmount(234.50);
        withGroceries(false);
        withUserType(UserType.LOYAL);
        makeRequest();
        assertCalculated(234.50, 21.725, DiscountType.LOYALTY_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_Loyal_With_Groceries() throws Exception {
        withAmount(234.50);
        withGroceries(true);
        withUserType(UserType.LOYAL);
        makeRequest();
        assertCalculated(234.50, 10.0, DiscountType.NONE);
    }

    @Test
    public void Should_Calculate_Discount_For_Regular() throws Exception {
        withAmount(234.50);
        withGroceries(false);
        withUserType(UserType.REGULAR);
        makeRequest();
        assertCalculated(234.50, 10.0, DiscountType.NONE);
    }

    @Test
    public void Should_Return_Bad_Request_Response_When_PaymentInfo_Is_Null() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content("")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_Return_Bad_Request_Response_When_UserInfo_Is_Null() throws Exception {
        paymentInfo.setUserInfo(null);
        String inputJson = mapToJson(paymentInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_Return_Bad_Request_Response_When_Amount_Is_Negative() throws Exception {
        withUserType(UserType.REGULAR);
        withAmount(-1343.31);
        String inputJson = mapToJson(paymentInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_Return_Bad_Request_Response_When_Amount_Is_Null() throws Exception {
        withUserType(UserType.REGULAR);
        withAmount(null);
        String inputJson = mapToJson(paymentInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    private void makeRequest() throws Exception {
        String inputJson = mapToJson(paymentInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 200);
        String content = mvcResult.getResponse().getContentAsString();
        discountInfo = mapFromJson(content, DiscountInfo.class);
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
