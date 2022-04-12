package com.lambakean.RationPlanner.exception;

public class UserNotLoggedInException extends RuntimeException {

    public UserNotLoggedInException(String message) {
        super(message);
    }
}
