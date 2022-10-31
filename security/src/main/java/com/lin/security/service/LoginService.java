package com.lin.security.service;

import com.lin.security.domain.LoginUser;
import com.lin.security.domain.ResponseResult;
import com.lin.security.domain.User;
import com.lin.security.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
LoginService->UserServiceImpl
 */
@Service
public class LoginService {
    // 假设的redis缓存
    public static volatile ConcurrentMap<String, LoginUser> userCathe = new ConcurrentHashMap();

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseResult login(User user) {
        //Authentication 调用authenticatie方法进行认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()); //用户登录时的用户名和密码
        // 这个authticate里面的LoginUser 是 UserServiceImpl 返回的那个
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //如果没有通过，返回提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        //如果验证通过了，使用userid生成jwt,再将jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long id = loginUser.getUser().getId(); //获取用户id
        String jwt = JwtUtil.createToken(30, id.toString()); //生成jwt

        //将完整的用户信息存入redis中，userid作为key
        userCathe.put("key:" + id, loginUser);

        //将token返回给前端
        HashMap<String, String> data = new HashMap<>();
        data.put("token", jwt);

        return new ResponseResult(200, "登录成功", data);
    }

    public ResponseResult logout() {
        // 获取SecurityContextHolder中的用户信息,因为会经过过滤器，注销接口实际已经设置了SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();

        // 缓存中删除用户信息
        userCathe.remove("key:" + loginUser.getUser().getId());

        return new ResponseResult(200, "注销成功", null);
    }
}
