package com.jd.b2b.shardingjdbc.service.impl;

import com.jd.b2b.shardingjdbc.domain.OrderItem;
import com.jd.b2b.shardingjdbc.mapper.OrderItemMapper;
import com.jd.b2b.shardingjdbc.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author songwanke
 * @date 2018/1/24
 */
@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Long insert(OrderItem model) {
        Long id = orderItemMapper.insert(model);
        return id;
    }

    @Override
    public void delete(Long orderItemId) {
        orderItemMapper.delete(orderItemId);
    }

    @Override
    @Transactional
    public List<OrderItem> selectAll() {
        List<OrderItem> orderItems = orderItemMapper.selectAll();
        return orderItems;
    }
}
