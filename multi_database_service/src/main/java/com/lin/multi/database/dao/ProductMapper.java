package com.lin.multi.database.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.multi.database.entity.Product;

import org.apache.ibatis.annotations.Mapper;

/**
 * 接口继承
 */
@Mapper
@DS("master")
public interface ProductMapper extends BaseMapper<Product> {

}
