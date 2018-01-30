package com.jd.b2b.shardingjdbc.service.impl;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.mapper.OrderMapper;
import com.jd.b2b.shardingjdbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void batchInsert(List<Order> orderList) {
        orderMapper.batchInsert(orderList);
    }

    @Override
    public List<Long> selectUnion(Long orderId) {
        return orderMapper.selectUnion(orderId);
    }

    /**
     * 校验or
     * @return
     */
    @Override
    public List<Order> selector() {
        return orderMapper.selector();
    }


    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }
}
