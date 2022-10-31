package com.lin.security.config;

import com.alibaba.fastjson.JSON;
import com.lin.security.domain.ResponseResult;
import com.lin.security.util.WebUtils;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败异常处理 JwtAuthticationTokenFilter或者登陆认证
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        AuthenticationException e) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "认证失败，请重新登录", null);
        String s = JSON.toJSONString(responseResult);
        WebUtils.renderString(httpServletResponse, s);
    }
}
