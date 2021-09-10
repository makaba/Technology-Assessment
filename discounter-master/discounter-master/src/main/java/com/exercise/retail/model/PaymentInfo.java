package com.exercise.retail.model;


/**
 * Data model class for the payment info
 */
public class PaymentInfo {

    private boolean containsGroceries;

    // Info can be some other type of data
    private UserInfo<String> userInfo;

    private Double amount;

    private Object otherData;

    // --------------------------------------------------------------------------------------------------
    // GETTERS AND SETTERS
    // --------------------------------------------------------------------------------------------------

    public boolean isContainingGroceries() {
        return containsGroceries;
    }

    public void setContainingGroceries(boolean containsGroceries) {
        this.containsGroceries = containsGroceries;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public UserInfo<String> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo<String> userInfo) {
        this.userInfo = userInfo;
    }

    public Object getOtherData() {
        return otherData;
    }

    public void setOtherData(Object otherData) {
        this.otherData = otherData;
    }
}
