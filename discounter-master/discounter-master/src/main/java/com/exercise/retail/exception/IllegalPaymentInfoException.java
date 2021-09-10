package com.exercise.retail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "PaymentInfo does not have the right structure")
public class IllegalPaymentInfoException extends RuntimeException {

    public IllegalPaymentInfoException() {
        super();
    }

    public IllegalPaymentInfoException(String message) {
        super(message);
    }

    public IllegalPaymentInfoException(Throwable cause) {
        super(cause);
    }
}
