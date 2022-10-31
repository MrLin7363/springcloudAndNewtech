package com.lin.security.controller;

import com.lin.security.domain.ResponseResult;
import com.lin.security.domain.User;
import com.lin.security.service.LoginService;
import com.lin.security.zhujie.Check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * pom引入security后,访问controller默认要登录
 */
@RestController
@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class UserController {
    //注入BCryptPasswordEncoder
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private LoginService loginService;

    /**
     * 自定义登录接口，仅对该接口放行
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@RequestBody User user) {
        return loginService.login(user);
    }

    /**
     * 通过token去注销
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseResult logout() {
        return loginService.logout();
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    // spring读到注解的时候把这个字符串当作一个表达式,当作方法去执行
    // 'admin'是什么权限能访问的意思 会去比较LoginUser的SimpleGrantedAuthority
    @PreAuthorize("hasAnyAuthority('admin','normal')")
//    @PreAuthorize("hasAuthority('admin')")
//    @PreAuthorize("hasRole('admin')")  会比较 ROLE_admin 少用
    public ResponseResult password() {
        // debug $2a$10$at7tErX8BzwVSTx9XoD3sewjrqWQRr.fAgqFNugclfvH.hg.g5TXa
        String encode = bCryptPasswordEncoder.encode("1234");
        System.out.println(encode);
        bCryptPasswordEncoder.matches("1234",encode);
        return new ResponseResult(200, "访问成功", new HashMap<>());
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @PreAuthorize("@myExpressionRoot.hasAuthority('normal')")
    public ResponseResult hello() {
        return new ResponseResult(200, "访问成功", new HashMap<>());
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResponseResult config() {
        return new ResponseResult(200, "访问成功", new HashMap<>());
    }
}
