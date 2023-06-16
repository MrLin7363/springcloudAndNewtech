 

package com.lin.spring.aop.nromalDemo;

import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormalController {

    public String test(Request request) {
        System.out.println("NormalController proccess");
        return request.getName();
    }

    @Data
    public static class Request {
        private String name;
    }
}
