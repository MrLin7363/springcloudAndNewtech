package com.lin.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 数据库查询的用户/或者缓存
 */
@Data
@AllArgsConstructor
public class User {

    private Long id;

    private String userName;

    private String password;
}
