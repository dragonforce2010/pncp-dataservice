package com.interview.pncp.dataservice.pojo;

/**
 * Created by michael.zhang on 7/5/17.
 */
public class RestResult<T> {
    private int responseCode;
    private String successmessage;
    private String errorMessage;
    private T data;

    public RestResult(){}

    public RestResult(int responseCode, T data, String successmessage, String errorMessage) {
        this.responseCode = responseCode;
        this.data = data;
        this.successmessage = successmessage;
        this.errorMessage = errorMessage;
    }

    public static <M> RestResult<M> newInstance(int responseCode, M data, String successmessage, String errorMessage){
        return new RestResult<>(responseCode, data, successmessage, errorMessage);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSuccessmessage() {
        return successmessage;
    }

    public void setSuccessmessage(String successmessage) {
        this.successmessage = successmessage;
    }
}
