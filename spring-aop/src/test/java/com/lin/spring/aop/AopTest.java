package com.lin.spring.aop;

import com.lin.spring.aop.annoDemo.SentinelController;
import com.lin.spring.aop.nromalDemo.NormalController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = AopApplication.class)
@RunWith(SpringRunner.class)
public class AopTest {

    @Autowired
    private SentinelController sentinelController;

    @Autowired
    private NormalController normalController;

    @Test
    public void testAnnoDemo() {
        final String result = sentinelController.test("lin");
        System.out.println(result);
    }

    @Test
    public void testNormalDemo() {
        NormalController.Request request = new NormalController.Request();
        request.setName("lin");
        final String result = normalController.test(request);
        System.out.println("result=" + result);
    }
}
