package com.magela.taskmanagementapi.application.dto;

public class ErrorResponse {
    private String message;
    private int errorCode;
    private Object errorDetail;

    public ErrorResponse(String message, int errorCode, Object errorDetail) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    public ErrorResponse(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetail = null;
    }

    // Getters e Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(Object errorDetail) {
        this.errorDetail = errorDetail;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", errorCode=" + errorCode +
                ", errorDetail='" + errorDetail + '\'' +
                '}';
    }
}