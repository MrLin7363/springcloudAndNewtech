package com.lin.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class ResponseResult {
    private int code;

    private String msg;

    HashMap<String, String> data;
}
