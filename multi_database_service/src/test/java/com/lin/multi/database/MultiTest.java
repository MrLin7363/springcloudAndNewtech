/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

package com.lin.multi.database;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.multi.database.dao.ProductMapper;
import com.lin.multi.database.dao.ProductionTestMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * desc:
 *
 * @author c30021507
 * @since 2022/9/6
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("development")
public class MultiTest {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductionTestMapper productionTestMapper;

    @Test
    public void test() {
        final Integer integer1 = productMapper.selectCount(new LambdaQueryWrapper<>());
        System.out.println(integer1);
        final Integer integer = productionTestMapper.selectCount(new LambdaQueryWrapper<>());
        System.out.println(integer);
    }
}
