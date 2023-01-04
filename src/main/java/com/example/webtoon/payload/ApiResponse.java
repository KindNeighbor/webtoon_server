package com.example.webtoon.payload;

import com.example.webtoon.type.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiResponse<T> {

    private int status;
    private HttpStatus statusMessage;
    private ResponseCode responseCode;
    private String responseMessage;
    private T data;

    public ApiResponse(HttpStatus statusMessage, ResponseCode responseCode, T data) {
        this.status = statusMessage.value();
        this.statusMessage = statusMessage;
        this.responseCode = responseCode;
        this.responseMessage = responseCode.getMessage();
        this.data = data;
    }
}