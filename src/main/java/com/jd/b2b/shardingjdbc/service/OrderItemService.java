package com.jd.b2b.shardingjdbc.service;

import com.jd.b2b.shardingjdbc.domain.OrderItem;

import java.util.List;

/**
 * @author songwanke
 * @date 2018/1/24
 */
public interface OrderItemService {

    Long insert(OrderItem model);

    void delete(Long orderItemId);

    List<OrderItem> selectAll();
}
