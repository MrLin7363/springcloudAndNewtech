package com.lin.security.domain;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * security的用户 定义一个类，实现UserDetails接口，把用户信息（从数据库中查询得到的信息）封装在其中
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    private List<String> permissions;

    // 只用转换一次,同时不用再序列化到redis流中(安全考虑啥的)
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * @return 返回权限信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 把permissions中String信息封装成SimpleGrantedAuthority对象
        if (authorities == null) {
            authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return authorities;
    }

    /**
     * 获取密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 判断是否没过期，true没过期，false过期了
     * 改为true
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否帐户未锁定,改为true
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证未过期，改为true
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用，改为true
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
