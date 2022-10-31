package com.lin.security.config;

import com.lin.security.filter.JwtAuthticationTokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security的一些类未注入spring需要手动注入
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthticationTokenFilter jwtAuthticationTokenFilter;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    /**
     * Secutity的密码加密对象
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //链式调用
        http
            // 允许跨域
            .cors().and()
            //关闭csrf没必要打开，因为前后端分离的项目就是现在访问要带的token,天然是抵抗csrf的。如果不关闭会去检验csrf_token，这里请求头没携带
            .csrf().disable()
            //不通过Session获取SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            // 对于登录接口 允许匿名访问，该接口不需要认证
            .antMatchers("/user/login").anonymous()
            // 基于配置的权限控制，这个接口需要admin权限才能访问
            .antMatchers("/user/config").hasAuthority("admin")
            // 除上面外的所有请求全部需要鉴权认证 就是需要加上token
            .anyRequest().authenticated();
        // 自定义过滤器在这个之前，并且自定义过滤器能够设置为已认证
        http.addFilterBefore(jwtAuthticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 自定义异常处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
    }
}
