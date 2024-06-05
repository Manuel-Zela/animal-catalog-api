package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ServiceResponse<T> {
    private boolean success;
    private T result;
    private List<String> messages = new ArrayList<>();

    public static <T> ServiceResponse ok(T result) {
        ServiceResponse response = new ServiceResponse();
        response.success = true;
        response.result = result;
        return response;
    }

    public static <T> ServiceResponse ok() {
        ServiceResponse response = new ServiceResponse();
        response.success = true;
        return response;
    }

    public static <T> ServiceResponse error(String message) {
        ServiceResponse response = new ServiceResponse();
        response.success = false;
        response.getMessages().add(message);
        return response;
    }
}
