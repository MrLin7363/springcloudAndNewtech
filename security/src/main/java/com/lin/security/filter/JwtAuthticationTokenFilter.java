package com.lin.security.filter;

import com.lin.security.domain.LoginUser;
import com.lin.security.jwt.JwtUtil;
import com.lin.security.service.LoginService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义过滤器
 */
@Component
public class JwtAuthticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            // 放行,如果是登录接口/user/login 执行用户密码校验的逻辑，登录逻辑 -> LoginService->UserServiceImpl
            // 如果其他接口 则 403
            filterChain.doFilter(request, response);
            // 拦截结束还是会到这里，所以return
            return;
        }
        // 跳过用户密码校验，直接访问走这里
        // 解析token
        String id = JwtUtil.parseToken(token);
        // 从缓存中获取用户信息
        LoginUser loginUser = LoginService.userCathe.get("key:" + id);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登陆");
        }
        // 存入SecurityContextHolder   构造函数 super.setAuthenticated(true)设置为已认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(loginUser,null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // 放行，否则拦截在这里了 -> LoginService->UserServiceImpl
        filterChain.doFilter(request, response);
    }
}
