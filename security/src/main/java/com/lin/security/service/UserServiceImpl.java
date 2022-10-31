package com.lin.security.service;

import com.lin.security.domain.LoginUser;
import com.lin.security.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义userDetailService
 * 根据用户查询数据库查取用户信息
 * 根据用户查询数据库获取权限信息
 * 封装UserDetails对象
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    /**
     * loadUserByUsername方法由DaoAuthemticationProvlder调用 主要工作是认证和授权
     * 返回的user会和用户输入的比较
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 默认的DelegatingPasswordEncoder加密器如果无加密算法需要{noop}1234   从数据库查
        User user = new User(1L, "user", "$2a$10$at7tErX8BzwVSTx9XoD3sewjrqWQRr.fAgqFNugclfvH.hg.g5TXa");
        // 根据用户名查询数据库,这里默认不查数据库了
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 根据用户名查询权限信息，设置到LoginUser中 从数据库中查
        List<String> rolesList = new ArrayList<>(Arrays.asList("normal"));
        // 后续加密的账号密码的USER和 用户输入的明文的USER密码账号 比较
        return new LoginUser(user, rolesList);
    }

}
