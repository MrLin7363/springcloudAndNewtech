package com.lin.security.expression;

import com.lin.security.domain.LoginUser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 重写自定义权限检验方法 SecurityExpressionRoot
 */
@Component
public class MyExpressionRoot {

    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 比较当前用户的权限是否包含authority
        return loginUser.getPermissions().contains(authority);
    }
}
