package com.lin.security.config;

import com.alibaba.fastjson.JSON;
import com.lin.security.domain.ResponseResult;
import com.lin.security.util.WebUtils;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权失败，403 针对用户权限 Controller里面的授权校验
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        AccessDeniedException e) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult(HttpStatus.FORBIDDEN.value(), "权限不足", null);
        String data = JSON.toJSONString(responseResult);
        WebUtils.renderString(httpServletResponse, data);
    }
}