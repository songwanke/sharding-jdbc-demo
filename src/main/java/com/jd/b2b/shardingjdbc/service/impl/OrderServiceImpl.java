package com.jd.b2b.shardingjdbc.service.impl;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.mapper.OrderMapper;
import com.jd.b2b.shardingjdbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author songwanke
 * @date 2018/1/24
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Long insert(Order model) {
        Long orderId = orderMapper.insert(model);
        return orderId;
    }

    @Override
    public void delete(Long orderId) {
        orderMapper.delete(orderId);
    }
}
