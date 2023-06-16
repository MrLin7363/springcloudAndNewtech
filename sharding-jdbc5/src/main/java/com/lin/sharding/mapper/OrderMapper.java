package com.lin.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.sharding.domain.Order;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
    List<Map<String, Object>> getOrderByTime(long begin, long end);
}
