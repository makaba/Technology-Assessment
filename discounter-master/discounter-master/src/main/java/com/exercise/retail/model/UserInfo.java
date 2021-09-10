package com.exercise.retail.model;

/**
 * Data model class for user info
 *
 * @param <E> additional data class containing more info about the user
 */
public class UserInfo<E> {

    private UserType type;
    private E data;

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
