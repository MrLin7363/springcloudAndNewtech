 

package com.lin.spring.aop.annoDemo;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelController {

    @SentinelAop(limitParams = {"sceneCode"}, resourceName = "sentinel")
    public String test(String name){
        System.out.println(name);
        return "SentinelController end";
    }
}
