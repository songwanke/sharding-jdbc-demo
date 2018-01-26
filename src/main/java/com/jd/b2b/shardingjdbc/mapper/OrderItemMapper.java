package com.jd.b2b.shardingjdbc.mapper;

import com.jd.b2b.shardingjdbc.domain.OrderItem;

import java.util.List;

public interface OrderItemMapper {

    Long insert(OrderItem model);
    
    void delete(Long orderItemId);
    
    List<OrderItem> selectAll();
    
}
