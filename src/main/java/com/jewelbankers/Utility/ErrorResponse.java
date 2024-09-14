package com.jewelbankers.Utility;

public class ErrorResponse {
    private String message;
    private String details;

    public ErrorResponse(String i, String details) {
        this.message = i;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

