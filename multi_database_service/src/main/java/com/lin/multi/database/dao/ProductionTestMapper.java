/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

package com.lin.multi.database.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.multi.database.entity.Product;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("test")
public interface ProductionTestMapper extends BaseMapper<Product> {
}
